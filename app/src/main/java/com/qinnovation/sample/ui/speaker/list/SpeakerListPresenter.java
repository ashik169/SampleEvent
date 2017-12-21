package com.qinnovation.sample.ui.speaker.list;

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
 * Created by qinnovation on 12/17/17.
 */

public class SpeakerListPresenter extends BasePresenter<SpeakerListContract.View> implements SpeakerListContract.Presenter {

    public SpeakerListPresenter(SpeakerListContract.View view, SQLiteDatabase sqLiteDatabase, PreferenceManager preferenceManager) {
        super(view, sqLiteDatabase, preferenceManager);
    }

    @Override
    public void fetchSpeakerList() {
        if (!isViewAttached()) {
            return;
        }
        view.progressBarVisibility(true);

        ISpeakerDataManager menuDataManager = new SpeakerDataManager(sqLiteDatabase);

        Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                if (!SpeakerListPresenter.this.isViewAttached()) {
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

        menuDataManager.getLocalSpeakerDetailList(handler);
    }
}
