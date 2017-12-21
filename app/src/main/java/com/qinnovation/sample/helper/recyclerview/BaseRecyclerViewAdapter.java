package com.qinnovation.sample.helper.recyclerview;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamed.ibrahim on 09-Oct-16.
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AdapterDataHandler<T> {

    protected List<T> list;

    public BaseRecyclerViewAdapter() {
        list = new ArrayList<>();
    }

    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void addItem(T t) {
        list.add(t);
        notifyItemInserted(getItemCount() - 1);
    }

    @Override
    public void addItemAt(int position, T t) {
//        int size = getItemCount();
//        if (position >= size)
//            return;

        list.add(position, t);
        notifyItemInserted(position);
    }

    @Override
    public void addItems(List<T> t) {
        list.clear();
        addAllItem(t);
    }

    @Override
    public void addAllItem(List<T> tList) {
        list.addAll(tList);
        notifyDataSetChanged();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public List<T> getItems() {
        return list;
    }

    @Override
    public void removeAll() {
        list.clear();
        notifyDataSetChanged();
    }

    @Override
    public void removeItemAt(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void removeItem(T t) {
        int index = list.indexOf(t);
        if (index == -1)
            return;
        list.remove(t);
        notifyItemRangeChanged(index, getItemCount());
    }

    public void updateUI(Intent intent) {
    }
}
