package com.qinnovation.sample.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.qinnovation.sample.application.MyApp;
import com.qinnovation.sample.helper.mvp.IMessageView;
import com.qinnovation.sample.preferences.PreferenceManager;
import com.qinnovation.sample.utils.NetworkUtil;

/**
 * Created by qinnovation on 12/13/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements IMessageView {

    private static final String TAG = BaseActivity.class.getSimpleName();

    private ProgressDialog dialog;

    private Context mContext;
    protected SQLiteDatabase sqLiteDatabase;

    protected PreferenceManager preferenceManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        preferenceManager = PreferenceManager.getInstance(getApplicationContext());

        sqLiteDatabase = ((MyApp) getApplicationContext()).getSQLiteDatabase();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void showToastMessage(@StringRes int message) {
        showToastMessage(message, Toast.LENGTH_LONG);
    }

    @Override
    public void showToastMessage(@StringRes final int message, final int duration) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Toast.makeText(BaseActivity.this, getString(message), duration).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void showToastMessage(String message) {
        showToastMessage(message, Toast.LENGTH_LONG);
    }

    @Override
    public void showToastMessage(final String message, final int duration) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, message, duration).show();
            }
        });
    }

    /*@Override
    public void showProgressDialog() {
        if (dialog != null)
            return;
        dialog = new ProgressDialog(this);
        dialog.setMessage("Processing...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }*/

    @Override
    public void showProgressDialog(final String message) {
        runOnUiThread(() -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.setMessage(TextUtils.isEmpty(message) ? "Processing..." : message);
                return;
            }

            dialog = new ProgressDialog(this);
            dialog.setMessage(TextUtils.isEmpty(message) ? "Processing..." : message);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        });
    }

    @Override
    public void showProgressDialog(@StringRes final int messageId) {
        runOnUiThread(() -> {
            String message = null;
            try {
                message = getString(messageId);
            } catch (Exception e) {
                message = "Something error occured";
                Log.e(TAG, "####showProgressDialog " + e.getMessage());
                e.printStackTrace();
            }
            showProgressDialog(message);
        });
    }

    @Override
    public void showInfoDialog(final String message) {
        /*if (mContext == null || ((Activity) mContext).isFinishing()) {
            return;
        }*/

    }

    @Override
    public void hideProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = null;
    }

    @Override
    public String getStringRes(@StringRes int stringId) {
        try {
            return getString(stringId);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getStringRes(@StringRes int resId, Object... formatArgs) {
        try {
            return getString(resId, formatArgs);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void closeActivity() {
        finish();
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtil.isInternetConnected(this);
    }

    @Override
    public void forceUpdateMaster(String message) {
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public SQLiteDatabase getDatabase() {
        return sqLiteDatabase;
    }

    public PreferenceManager getPreferenceManager() {
        return preferenceManager;
    }
}