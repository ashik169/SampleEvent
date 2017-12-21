package com.qinnovation.sample.helper.multiselect;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.qinnovation.sample.R;
import com.qinnovation.sample.helper.MasterData;
import com.qinnovation.sample.utils.TextColorUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * Created by mohamed.ibrahim on 16-Oct-17.
 */

public class SpinnerMultiSelectCallBack implements MultiSelectCallBack<MasterData, SpinnerMultiSelectCallBack.SpinnerVH> {


    private String sFilter = null;

    public SpinnerMultiSelectCallBack() {
    }

    @Override
    public SpinnerVH onCreateView(LayoutInflater inflater, ViewGroup parent, int viewType,
                                  OnCheckChangedListener<MasterData, SpinnerVH> onCheckChangedListener) {
        View view = inflater.inflate(R.layout.simple_list_item_check_1, parent, false);
        return new SpinnerVH(view, onCheckChangedListener);
    }

    @Override
    public void onBindView(SpinnerVH holder, MasterData spinnerData, int position) {
        holder.checkBox.setText(spinnerData.getValue());
        holder.checkBox.setSingleLine(false);
        holder.checkBox.setChecked(spinnerData.isChecked());

        if (!TextUtils.isEmpty(sFilter)) {
            holder.checkBox.setText(TextColorUtil.getInstance().getSpanText(holder.checkBox.getContext(),
                    spinnerData.getValue(), sFilter), TextView.BufferType.SPANNABLE);
        }
    }

    @Override
    public synchronized boolean filterData(String filter, MasterData data) {
        sFilter = filter;
        if (TextUtils.isEmpty(filter) || data == null)
            return true;
        String keyword = filter.toLowerCase().trim();
        final String value = data.getValue().toLowerCase();
        return value.contains(keyword);
    }

    @Override
    public void onCheckedChange(SpinnerVH spinnerVH, MasterData spinnerData, boolean isChecked) {
        spinnerData.setChecked(isChecked);
    }

    @Override
    public List<MasterData> getCheckedList(List<MasterData> list) {
        List<MasterData> spinnerDataList = new ArrayList<>();
        for (MasterData spinnerData : list) {
            if (spinnerData.isChecked())
                spinnerDataList.add(spinnerData);
        }
        return spinnerDataList;
    }

    public static class SpinnerVH extends RecyclerView.ViewHolder {

        @BindView(R.id.check1)
        CheckBox checkBox;

        private OnCheckChangedListener<MasterData, SpinnerVH> onViewClickListener;

        public SpinnerVH(View itemView, OnCheckChangedListener<MasterData, SpinnerVH> onViewClickListener) {
            super(itemView);
            this.onViewClickListener = onViewClickListener;
            ButterKnife.bind(this, itemView);
        }

        @OnCheckedChanged(R.id.check1)
        void onCheckedChangeListener(CompoundButton buttonView, boolean isChecked) {
            int position = getAdapterPosition();
            if (position == RecyclerView.NO_POSITION) {
                return;
            }

            if (onViewClickListener != null) {
                onViewClickListener.onCheckedChange(this, isChecked, position);
            }
        }
    }
}
