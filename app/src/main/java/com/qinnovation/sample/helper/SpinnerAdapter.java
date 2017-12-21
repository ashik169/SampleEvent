package com.qinnovation.sample.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qinnovation.sample.helper.MasterData;

import java.util.ArrayList;
import java.util.Locale;

/**
 * SpinnerAdapter extends {@link BaseAdapter}
 */
@SuppressLint("InflateParams")
public class SpinnerAdapter extends BaseAdapter {
    Context context;
    ArrayList<MasterData> masterData = new ArrayList<MasterData>();
    LayoutInflater inflator;
    ArrayList<MasterData> arraylist;

    /**
     * {@link java.lang.reflect.Constructor}
     *
     * @param ctx  {@link Context}
     * @param data {@link ArrayList}<{@link MasterData}>
     */
    public SpinnerAdapter(Context ctx, ArrayList<MasterData> data) {
        this.context = ctx;
        masterData = data;
        inflator = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(masterData);
    }

    @Override
    public int getCount() {
        return masterData.size();
    }

    @Override
    public MasterData getItem(int arg0) {
        return masterData.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return masterData.get(arg0).getValueId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        if (convertView == null) {
            convertView = inflator.inflate(android.R.layout.simple_list_item_1, null);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(masterData.get(position).getValue());

        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        masterData.clear();
        if (charText.length() == 0) {
            masterData.addAll(arraylist);
        } else {
            for (MasterData spData : arraylist) {
                if (spData.getValue().toLowerCase(Locale.getDefault()).contains(charText)) {
                    masterData.add(spData);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void reset() {
        masterData.clear();
        masterData.addAll(arraylist);
        notifyDataSetChanged();
    }
}
