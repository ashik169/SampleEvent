package com.qinnovation.sample.ui.base;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;

import com.qinnovation.sample.helper.mvp.IMessageView;

/**
 * Created by qinnovation on 12/13/17.
 */

public abstract class BaseDialogFragment extends DialogFragment implements IMessageView {

    private BaseActivity baseActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            baseActivity = (BaseActivity) context;
        }
    }

    @Override
    public void showToastMessage(String message, int duration) {
        if (baseActivity != null)
            baseActivity.showToastMessage(message, duration);
    }

    @Override
    public void showToastMessage(String message) {
        if (baseActivity != null)
            baseActivity.showToastMessage(message);
    }

    @Override
    public void showToastMessage(@StringRes int message, int duration) {
        if (baseActivity != null)
            baseActivity.showToastMessage(message, duration);
    }

    @Override
    public void showToastMessage(@StringRes int message) {
        if (baseActivity != null)
            baseActivity.showToastMessage(message);
    }

    /*@Override
    public void showProgressDialog() {
        if (baseActivity != null)
            baseActivity.showProgressDialog(null);
    }*/

    @Override
    public void showProgressDialog(String message) {
        if (baseActivity != null)
            baseActivity.showProgressDialog(message);
    }

    @Override
    public void showProgressDialog(@StringRes int messageId) {
        if (baseActivity != null)
            baseActivity.showProgressDialog(messageId);
    }

    @Override
    public void hideProgressDialog() {
        if (baseActivity != null)
            baseActivity.hideProgressDialog();
    }

    @Override
    public boolean isNetworkConnected() {
        if (baseActivity != null) {
            return baseActivity.isNetworkConnected();
        }
        return false;
    }

    @Override
    public void forceUpdateMaster(String message) {
        if (baseActivity != null)
            baseActivity.forceUpdateMaster(message);
    }

    @Override
    public String getStringRes(@StringRes int stringId) {
        return getString(stringId);
    }

    @Override
    public String getStringRes(@StringRes int resId, Object... formatArgs) {
        return getString(resId, formatArgs);
    }

    @Override
    public void showInfoDialog(String message) {
        if (baseActivity != null)
            baseActivity.showInfoDialog(message);
    }

    @Override
    public void closeActivity() {
        if (baseActivity != null) {
            baseActivity.closeActivity();
        }
    }
}
