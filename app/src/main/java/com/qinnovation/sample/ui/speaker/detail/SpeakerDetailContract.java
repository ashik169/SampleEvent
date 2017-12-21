package com.qinnovation.sample.ui.speaker.detail;

import com.qinnovation.sample.helper.mvp.IBasePresenter;
import com.qinnovation.sample.helper.mvp.IMessageView;
import com.qinnovation.sample.ui.speaker.SpeakerDetail;

import java.util.List;

/**
 * Created by qinnovation on 12/17/17.
 */

public interface SpeakerDetailContract {

    interface View extends IMessageView {

        void populateSessionList(List<SpeakerDetail> speakerDetailList);

        void progressSessionBarVisibility(boolean visibility);
    }

    interface Presenter extends IBasePresenter<View> {
        void fetchSessionList(int limitCount);
    }

}
