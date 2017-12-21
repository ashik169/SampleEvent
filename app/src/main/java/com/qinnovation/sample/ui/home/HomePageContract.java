package com.qinnovation.sample.ui.home;

import com.qinnovation.sample.helper.mvp.IBasePresenter;
import com.qinnovation.sample.helper.mvp.IMessageView;
import com.qinnovation.sample.master.model.MenuDetail;
import com.qinnovation.sample.ui.speaker.SpeakerDetail;

import java.util.List;

/**
 * Created by qinnovation on 12/17/17.
 */

public interface HomePageContract {

    interface View extends IMessageView {

        void populateMenuList(List<MenuDetail> menuDetailList);

        void progressBarVisibility(boolean visibility);

        void populateSpeakerList(List<SpeakerDetail> speakerDetailList);
    }

    interface Presenter extends IBasePresenter<View> {
        void fetchMenuList();

        void fetchSpeakerList(int count);
    }

}
