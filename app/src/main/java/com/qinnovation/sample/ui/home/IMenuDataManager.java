package com.qinnovation.sample.ui.home;

import android.os.Handler;

/**
 * Created by qinnovation on 12/17/17.
 */

interface IMenuDataManager {

    void getLocalMenuDetailList(Handler handler);

    void getMenuDetailList(Handler handler);
}
