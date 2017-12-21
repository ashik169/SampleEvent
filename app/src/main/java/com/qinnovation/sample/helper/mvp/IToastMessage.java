package com.qinnovation.sample.helper.mvp;

import android.support.annotation.StringRes;

public interface IToastMessage {

    void showToastMessage(String message);

    void showToastMessage(@StringRes int message);
}
