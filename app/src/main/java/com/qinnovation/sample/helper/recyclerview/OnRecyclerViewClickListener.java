package com.qinnovation.sample.helper.recyclerview;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mohamed.ibrahim on 09-Oct-16.
 */

public interface OnRecyclerViewClickListener {
    void onItemViewClick(Object obj, ViewGroup parent, View view, int position);
}
