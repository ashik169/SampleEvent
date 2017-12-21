package com.qinnovation.sample.ui.home;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.qinnovation.sample.helper.mvp.BasePresenter;
import com.qinnovation.sample.master.model.MenuDetail;
import com.qinnovation.sample.preferences.PreferenceManager;
import com.qinnovation.sample.ui.speaker.ISpeakerDataManager;
import com.qinnovation.sample.ui.speaker.SpeakerDataManager;
import com.qinnovation.sample.ui.speaker.SpeakerDetail;

import java.util.List;

import static com.qinnovation.sample.utils.Constants.MESSAGE;
import static com.qinnovation.sample.utils.Constants.SUCCESS;

/**
 * Created by qinnovation on 12/17/17.
 */

public class HomePagePresenter extends BasePresenter<HomePageContract.View> implements HomePageContract.Presenter {


    public HomePagePresenter(HomePageContract.View view, SQLiteDatabase sqLiteDatabase, PreferenceManager preferenceManager) {
        super(view, sqLiteDatabase, preferenceManager);
    }

    public void fetchMenuList() {
        if (!isViewAttached()) {
            return;
        }
        view.progressBarVisibility(true);

        IMenuDataManager menuDataManager = new MenuDataManager(sqLiteDatabase);

        Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                if (!HomePagePresenter.this.isViewAttached()) {
                    return false;
                }

                Bundle data = message.getData();

                boolean success = data.getBoolean(SUCCESS);
                String msg = data.getString(MESSAGE, "Unknown message");

                if (!success) {
                    view.showToastMessage(msg);
                } else {
                    //noinspection unchecked
                    view.populateMenuList((List<MenuDetail>) message.obj);
                }
                view.progressBarVisibility(false);
                return false;
            }
        });
        menuDataManager.getLocalMenuDetailList(handler);
    }

    @Override
    public void fetchSpeakerList(int limitCount) {
        if (!isViewAttached()) {
            return;
        }
        view.progressBarVisibility(true);

        ISpeakerDataManager speakerDataManager = new SpeakerDataManager(sqLiteDatabase);

        Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                if (!HomePagePresenter.this.isViewAttached()) {
                    return false;
                }

                Bundle data = message.getData();

                boolean success = data.getBoolean(SUCCESS);
                String msg = data.getString(MESSAGE, "Unknown message");

                if (!success) {
                    view.showToastMessage(msg);
                } else {
                    //noinspection unchecked
                    view.populateSpeakerList((List<SpeakerDetail>) message.obj);
                }
                view.progressBarVisibility(false);
                return false;
            }
        });
        speakerDataManager.getOfflineSpeakerList(handler, 3);
    }
}
