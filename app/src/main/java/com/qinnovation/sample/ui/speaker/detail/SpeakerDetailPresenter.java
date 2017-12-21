package com.qinnovation.sample.ui.speaker.detail;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.qinnovation.sample.helper.mvp.BasePresenter;
import com.qinnovation.sample.preferences.PreferenceManager;
import com.qinnovation.sample.ui.speaker.ISpeakerDataManager;
import com.qinnovation.sample.ui.speaker.SpeakerDataManager;
import com.qinnovation.sample.ui.speaker.SpeakerDetail;

import java.util.List;

import static com.qinnovation.sample.utils.Constants.MESSAGE;
import static com.qinnovation.sample.utils.Constants.SUCCESS;

/**
 * Created by Qinnovation on 12/18/2017.
 */

public class SpeakerDetailPresenter extends BasePresenter<SpeakerDetailContract.View> implements SpeakerDetailContract.Presenter {

    public SpeakerDetailPresenter(SpeakerDetailContract.View view, SQLiteDatabase sqLiteDatabase, PreferenceManager preferenceManager) {
        super(view, sqLiteDatabase, preferenceManager);
    }

    @Override
    public void fetchSessionList(int limitCount) {
        if (!isViewAttached()) {
            return;
        }
        view.progressSessionBarVisibility(true);

        ISpeakerDataManager menuDataManager = new SpeakerDataManager(sqLiteDatabase);

        Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                if (!SpeakerDetailPresenter.this.isViewAttached()) {
                    return false;
                }

                Bundle data = message.getData();

                boolean success = data.getBoolean(SUCCESS);
                String msg = data.getString(MESSAGE, "Unknown message");

                if (!success) {
                    view.showToastMessage(msg);
                } else {
                    //noinspection unchecked
                    view.populateSessionList((List<SpeakerDetail>) message.obj);
                }
                view.progressSessionBarVisibility(false);
                return false;
            }
        });

        menuDataManager.getOfflineSpeakerList(handler, limitCount);
    }
}
