package com.qinnovation.sample.helper.multiselect;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by mohamed.ibrahim on 16-Oct-17.
 */

public interface MultiSelectCallBack<T, VH extends RecyclerView.ViewHolder> {

    VH onCreateView(LayoutInflater inflater, ViewGroup parent, int viewType, OnCheckChangedListener<T, VH> onCheckChangedListener);

    void onBindView(VH holder, T t, int position);

    boolean filterData(String filterText, T list);

    void onCheckedChange(VH vh, T t, boolean isChecked);

    List<T> getCheckedList(List<T> list);

    public interface OnCheckChangedListener<T, VH extends RecyclerView.ViewHolder> {
        void onCheckedChange(VH vh, boolean isChecked, int position);

        T getItem(int position);
    }
}
