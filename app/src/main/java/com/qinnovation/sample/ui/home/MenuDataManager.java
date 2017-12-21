package com.qinnovation.sample.ui.home;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import com.qinnovation.sample.db.DBConstants;
import com.qinnovation.sample.master.model.MenuDetail;
import com.qinnovation.sample.webservice.IMenuService;
import com.qinnovation.sample.webservice.WebServiceUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.qinnovation.sample.utils.Constants.MESSAGE;
import static com.qinnovation.sample.utils.Constants.SUCCESS;

/**
 * Created by qinnovation on 12/17/17.
 */

class MenuDataManager implements IMenuDataManager {

    private static final String TAG = MenuDataManager.class.getCanonicalName();

    private SQLiteDatabase db;

    public MenuDataManager(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public void getLocalMenuDetailList(Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Bundle bundle = new Bundle();
                Message message = Message.obtain();
                message.setData(bundle);

                long speakerCount = 0;
                try {
                    String sumQuery = "SELECT COUNT(*) FROM " + DBConstants.TABLE_MENU;
                    speakerCount = DatabaseUtils.longForQuery(db, sumQuery, null);
                } catch (Exception e) {
                    bundle.putString(MESSAGE, " Failed to check db");
                    handler.sendMessage(message);
                    return;
                }

                if (speakerCount > 0) {
                    // fetch from db
                    try {
                        message.obj = fetchDBMenuList();
                        bundle.putString(MESSAGE, "Menu Detail Received successful");
                        bundle.putBoolean(SUCCESS, true);
                        handler.sendMessage(message);
                    } catch (Exception e) {
                        bundle.putString(MESSAGE, e.getMessage());
                        handler.sendMessage(message);
                    }
                } else {
                    // fetch from service then save into db finally update into ui
                    try {
                        message.obj = syncMenuList();
                        bundle.putString(MESSAGE, "Menu Detail Received successful");
                        bundle.putBoolean(SUCCESS, true);
                        handler.sendMessage(message);
                    } catch (Exception e) {
                        bundle.putString(MESSAGE, e.getMessage());
                        handler.sendMessage(message);
                    }
                }
                // validate db size if size is zero
                // fetchData from service else fetch from db
            }
        }).start();
    }

    private List<MenuDetail> syncMenuList() throws Exception {
        Log.d(TAG, "MenuDataManager.syncMenuList");
        IMenuService iMenuService = WebServiceUtil.getInstance().createInstance(IMenuService.class);
        Call<List<MenuDetail>> listCall = iMenuService.getMenuList();

        try {
            Response<List<MenuDetail>> response = listCall.execute();
            int code = response.code();
            if (!response.isSuccessful()) {
                throw new Exception("Failed to get menu detail - " + code);
            }

            if (code == 200) {
                List<MenuDetail> speakerDetailList = response.body();
                if (speakerDetailList == null) {
                    throw new Exception("menu detail is null - " + code);
                }

//                Collections.sort(speakerDetailList, (menuDetail, t1) -> menuDetail.getId().compareTo(t1.getId()));

                // save int db
                saveMenuDetail(speakerDetailList);
                return speakerDetailList;
            } else {
                String errorMsg = null;
                ResponseBody responseBody = response.errorBody();
                try {
                    errorMsg = responseBody.string();
                } catch (IOException e) {
                    e.printStackTrace();
                    errorMsg = e.getMessage();
                }
                throw new Exception("errorMessage - " + errorMsg + " - " + code);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void saveMenuDetail(List<MenuDetail> menuDetailList) throws Exception {

        for (MenuDetail menuDetail : menuDetailList) {

            ContentValues values = new ContentValues();
            values.put(DBConstants.COLUMN_MENU_ID, menuDetail.getId());
            values.put(DBConstants.COLUMN_MENU_NAME, menuDetail.getName());
            values.put(DBConstants.COLUMN_MENU_ORDER, menuDetail.getOrder());

            long insert = db.insertWithOnConflict(DBConstants.TABLE_MENU, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            if (insert <= 0) {
                throw new Exception("Failed to insert - " + menuDetail.getName());
            }
        }
    }

    private List<MenuDetail> fetchDBMenuList() {
        Log.d(TAG, "####MenuDataManager.fetchDBMenuList");
        List<MenuDetail> menuDetailList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DBConstants.TABLE_MENU + " ORDER BY " + DBConstants.COLUMN_MENU_ORDER + " ASC";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                menuDetailList.add(new MenuDetail().parseCursorDetail(cursor));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return menuDetailList;
    }

    @Override
    public void getMenuDetailList(Handler handler) {
        Bundle bundle = new Bundle();
        Message message = Message.obtain();
        message.setData(bundle);

        IMenuService iMenuService = WebServiceUtil.getInstance().createInstance(IMenuService.class);
        Call<List<MenuDetail>> menuList = iMenuService.getMenuList();

        menuList.enqueue(new Callback<List<MenuDetail>>() {
            @Override
            public void onResponse(@NonNull Call<List<MenuDetail>> call, @NonNull Response<List<MenuDetail>> response) {
                int code = response.code();
                if (!response.isSuccessful()) {
                    bundle.putString(MESSAGE, "Failed to get menu detail - " + code);
                    handler.sendMessage(message);
                    return;
                }

                if (code == 200) {
                    List<MenuDetail> menuDetailList = response.body();
                    if (menuDetailList == null) {
                        bundle.putString(MESSAGE, "menu detail is null - " + code);
                        handler.sendMessage(message);
                        return;
                    }

                    Collections.sort(menuDetailList, (menuDetail, t1) -> menuDetail.getId().compareTo(t1.getId()));

                    for (MenuDetail menuDetail : menuDetailList) {
                        Log.d(TAG, "####Menu Detail - " +menuDetail);
                    }
                    message.obj = menuDetailList;
                    bundle.putString(MESSAGE, "Menu Detail Received successful");
                    bundle.putBoolean(SUCCESS, true);
                    handler.sendMessage(message);
                } else {
                    String errorMsg = null;
                    ResponseBody responseBody = response.errorBody();
                    try {
                        errorMsg = responseBody.string();
                    } catch (IOException e) {
                        e.printStackTrace();
                        errorMsg = e.getMessage();
                    }
                    bundle.putString(MESSAGE, "errorMessage - " + errorMsg + " - " + code);
                    handler.sendMessage(message);
                    return;
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<MenuDetail>> call, @NonNull Throwable t) {
                bundle.putString(MESSAGE, t.getMessage());
                handler.sendMessage(message);
            }
        });

    }
}
