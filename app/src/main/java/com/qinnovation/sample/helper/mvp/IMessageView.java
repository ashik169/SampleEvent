package com.qinnovation.sample.helper.mvp;

import android.support.annotation.StringRes;

/**
 * Created by mohamed.ibrahim on 07-Mar-17.
 */

public interface IMessageView {

    void showToastMessage(String message);

    void showToastMessage(String message, int duration);

    void showToastMessage(@StringRes int message);

    void showToastMessage(@StringRes int message, int duration);


    void showProgressDialog(String message);

    void showProgressDialog(@StringRes int messageId);

    void hideProgressDialog();


    String getStringRes(@StringRes int stringId);

    String getStringRes(@StringRes int resId, Object... formatArgs);


    boolean isNetworkConnected();

    void forceUpdateMaster(String message);

    void showInfoDialog(String message);

    void closeActivity();
}
