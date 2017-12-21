package com.qinnovation.sample.helper.recyclerview;

import android.view.View;
import android.view.ViewGroup;

public interface IRecyclerViewClickListener<T> {
    void onItemViewClicked(T t, ViewGroup parent, View view, int position);
}
