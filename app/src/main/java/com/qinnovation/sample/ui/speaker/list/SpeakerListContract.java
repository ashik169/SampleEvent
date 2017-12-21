package com.qinnovation.sample.ui.speaker.list;

import com.qinnovation.sample.helper.mvp.IBasePresenter;
import com.qinnovation.sample.helper.mvp.IMessageView;
import com.qinnovation.sample.ui.speaker.SpeakerDetail;

import java.util.List;

/**
 * Created by qinnovation on 12/17/17.
 */

public interface SpeakerListContract {

    interface View extends IMessageView {

        void populateSpeakerList(List<SpeakerDetail> menuDetailList);

        void progressBarVisibility(boolean visibility);
    }

    interface Presenter extends IBasePresenter<View> {
        void fetchSpeakerList();
    }

}
