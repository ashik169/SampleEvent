package com.qinnovation.sample.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class MySpinnerAdapter extends BaseAdapter {

    private final LayoutInflater inflater;

    private List<MasterData> masterDataList;
    private boolean singleLine;

    public MySpinnerAdapter(Context context) {
//        Context context1 = context;
        this.masterDataList = new ArrayList<>();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        singleLine = true;
    }

    public void setSingleLine(boolean singleLine) {
        this.singleLine = singleLine;
    }

    @Override
    public int getCount() {
        return masterDataList.size();
    }

    @Override
    public MasterData getItem(int position) {
        return masterDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (getCount() > 0)
            return (long) getItem(position).getValueId();
        else
            return -1;
    }

    @SuppressLint("ViewHolder")
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        TextView textView = (TextView) row.findViewById(android.R.id.text1);
        if (!singleLine)
            textView.setSingleLine(false);
        textView.setText(getItem(position).getValue());
        return row;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        TextView textView = (TextView) row.findViewById(android.R.id.text1);
        if (!singleLine)
            textView.setSingleLine(false);
        textView.setText(getItem(position).getValue());
        return row;
    }

    public void addItems(List<MasterData> list) {
        masterDataList.clear();
        if (list == null) {
            notifyDataSetChanged();
            return;
        }

        masterDataList.addAll(list);
        notifyDataSetChanged();
    }

    public int findIndexById(int id) {
        int selectedIndex = 0;
        int size = masterDataList.size();
        for (int i = 0; i < size; i++) {
            if (id == masterDataList.get(i).getValueId()) {
                selectedIndex = i;
                break;
            }
        }
        return selectedIndex;
    }
}