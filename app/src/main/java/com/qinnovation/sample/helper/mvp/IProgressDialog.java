package com.qinnovation.sample.helper.mvp;

import android.support.annotation.StringRes;


public interface IProgressDialog {

    void showProgressDialog();

    void updateProgressDialogMessage(String message);

    void updateProgressDialogMessage(@StringRes int messageId);

    void hideProgressDialog();
}
