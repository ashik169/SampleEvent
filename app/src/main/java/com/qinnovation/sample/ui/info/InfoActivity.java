package com.qinnovation.sample.ui.info;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qinnovation.sample.R;
import com.qinnovation.sample.ui.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by qinnovation on 12/14/17.
 */

public class InfoActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);

    }


}
