package com.qinnovation.sample.ui.speaker;

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
import com.qinnovation.sample.webservice.ISpeakerService;
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

public class SpeakerDataManager implements ISpeakerDataManager {

    private static final String TAG = SpeakerDataManager.class.getCanonicalName();

    private SQLiteDatabase db;

    public SpeakerDataManager(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public void getLocalSpeakerDetailList(Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Bundle bundle = new Bundle();
                Message message = Message.obtain();
                message.setData(bundle);

                long speakerCount = 0;
                try {
                    String sumQuery = "SELECT COUNT(*) FROM " + DBConstants.TABLE_SPEAKER;
                    speakerCount = DatabaseUtils.longForQuery(db, sumQuery, null);
                } catch (Exception e) {
                    bundle.putString(MESSAGE, " Failed to check db");
                    handler.sendMessage(message);
                    return;
                }

                if (speakerCount > 0) {
                    // fetch from db
                    try {
                        message.obj = fetchDBSpeakerList();
                        bundle.putString(MESSAGE, "Speaker Detail Received successful");
                        bundle.putBoolean(SUCCESS, true);
                        handler.sendMessage(message);
                    } catch (Exception e) {
                        bundle.putString(MESSAGE, e.getMessage());
                        handler.sendMessage(message);
                    }
                } else {
                    // fetch from service then save into db finally update into ui
                    try {
                        message.obj = syncSpeakerList();
                        bundle.putString(MESSAGE, "Speaker Detail Received successful");
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

    private List<SpeakerDetail> syncSpeakerList() throws Exception {
        Log.d(TAG, "SpeakerDataManager.syncSpeakerList");
        ISpeakerService iSpeakerService = WebServiceUtil.getInstance().createInstance(ISpeakerService.class);
        Call<List<SpeakerDetail>> speakerList = iSpeakerService.getSpeakerList();

        try {
            Response<List<SpeakerDetail>> response = speakerList.execute();
            int code = response.code();
            if (!response.isSuccessful()) {
                throw new Exception("Failed to get speaker detail - " + code);
            }

            if (code == 200) {
                List<SpeakerDetail> speakerDetailList = response.body();
                if (speakerDetailList == null) {
                    throw new Exception("speaker detail is null - " + code);
                }

//                Collections.sort(speakerDetailList, (menuDetail, t1) -> menuDetail.getId().compareTo(t1.getId()));

                // save int db
                saveSpeakerDetail(speakerDetailList);
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

    private void saveSpeakerDetail(List<SpeakerDetail> speakerDetailList) throws Exception {

        for (SpeakerDetail speakerDetail : speakerDetailList) {

            ContentValues values = new ContentValues();
            values.put(DBConstants.COLUMN_SPEAKER_ID, speakerDetail.getId());
            values.put(DBConstants.COLUMN_SPEAKER_NAME, speakerDetail.getName());
            values.put(DBConstants.COLUMN_SPEAKER_EMAIL, speakerDetail.getEmail());
            values.put(DBConstants.COLUMN_SPEAKER_IMG_URL, speakerDetail.getImage());
            values.put(DBConstants.COLUMN_SPEAKER_ORDER, speakerDetail.getOrder());

            long insert = db.insertWithOnConflict(DBConstants.TABLE_SPEAKER, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            if (insert <= 0) {
                throw new Exception("Failed to insert - " + speakerDetail.getName());
            }
        }
    }

    private List<SpeakerDetail> fetchDBSpeakerList() {
        Log.d(TAG, "####SpeakerDataManager.fetchDBSpeakerList");
        List<SpeakerDetail>speakerDetailList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DBConstants.TABLE_SPEAKER + " ORDER BY " + DBConstants.COLUMN_SPEAKER_ORDER + " ASC";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                speakerDetailList.add(new SpeakerDetail().parseCursorDetail(cursor));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return speakerDetailList;
    }

    private List<SpeakerDetail> fetchDBSpeakerList(int limitCount) {
        Log.d(TAG, "####SpeakerDataManager.fetchDBSpeakerList Limit");
        List<SpeakerDetail>speakerDetailList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DBConstants.TABLE_SPEAKER + " ORDER BY " + DBConstants.COLUMN_SPEAKER_ORDER + " ASC LIMIT " + limitCount;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                speakerDetailList.add(new SpeakerDetail().parseCursorDetail(cursor));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return speakerDetailList;
    }

    @Override
    public void getSpeakerDetailList(Handler handler) {
        Bundle bundle = new Bundle();
        Message message = Message.obtain();
        message.setData(bundle);

        ISpeakerService iSpeakerService = WebServiceUtil.getInstance().createInstance(ISpeakerService.class);
        Call<List<SpeakerDetail>> speakerList = iSpeakerService.getSpeakerList();

        speakerList.enqueue(new Callback<List<SpeakerDetail>>() {
            @Override
            public void onResponse(@NonNull Call<List<SpeakerDetail>> call, @NonNull Response<List<SpeakerDetail>> response) {
                int code = response.code();
                if (!response.isSuccessful()) {
                    bundle.putString(MESSAGE, "Failed to get speaker detail - " + code);
                    handler.sendMessage(message);
                    return;
                }

                if (code == 200) {
                    List<SpeakerDetail> speakerDetailList = response.body();
                    if (speakerDetailList == null) {
                        bundle.putString(MESSAGE, "speaker detail is null - " + code);
                        handler.sendMessage(message);
                        return;
                    }

                    Collections.sort(speakerDetailList, (menuDetail, t1) -> menuDetail.getId().compareTo(t1.getId()));

                    for (SpeakerDetail speakerDetail : speakerDetailList) {
                        Log.d(TAG, "####Speaker Detail - " + speakerDetail);
                    }
                    message.obj = speakerDetailList;
                    bundle.putString(MESSAGE, "Speaker Detail Received successful");
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
            public void onFailure(@NonNull Call<List<SpeakerDetail>> call, @NonNull Throwable t) {
                bundle.putString(MESSAGE, t.getMessage());
                handler.sendMessage(message);
            }
        });
    }

    @Override
    public void getOfflineSpeakerList(Handler handler, int count) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "####SpeakerDataManager.fetchDBSpeakerList");

                Message message = handler.obtainMessage();
                Bundle bundle = new Bundle();
                message.setData(bundle);
                try {
                    message.obj = fetchDBSpeakerList(count);
                    bundle.putString(MESSAGE, "Data has been fetched successful");
                    bundle.putBoolean(SUCCESS, true);
                    handler.sendMessage(message);
                } catch (Exception e) {
                    bundle.putString(MESSAGE, "Failed to fetch data");
                    handler.sendMessage(message);
                }
            }
        }).start();
    }
}
