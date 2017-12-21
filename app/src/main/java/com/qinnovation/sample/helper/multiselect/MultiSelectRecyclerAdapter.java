package com.qinnovation.sample.helper.multiselect;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamed.ibrahim on 16-Oct-17.
 */

public class MultiSelectRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH>
        implements MultiSelectCallBack.OnCheckChangedListener<T, VH> {


    private final MultiSelectCallBack<T, VH> multiSelectCallBack;
    private final LayoutInflater inflater;

    private List<T> backUpList;
    private List<T> list;

    public MultiSelectRecyclerAdapter(Context context, MultiSelectCallBack<T, VH> multiSelectCallBack) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.backUpList = new ArrayList<>();
        this.list = new ArrayList<>();
        this.multiSelectCallBack = multiSelectCallBack;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return multiSelectCallBack.onCreateView(inflater, parent, viewType, this);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        T item = getItem(position);
        if (item == null)
            return;
        multiSelectCallBack.onBindView(holder, item, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filter(String filterText) {
        if (TextUtils.isEmpty(filterText)) {
            multiSelectCallBack.filterData(filterText, null);
            list.clear();
            list.addAll(backUpList);
            notifyDataSetChanged();
            return;
        }
        list.clear();
        for (T t : backUpList) {
            if (multiSelectCallBack.filterData(filterText, t)) {
                list.add(t);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public void onCheckedChange(VH vh, boolean isChecked, int position) {
        multiSelectCallBack.onCheckedChange(vh, getItem(position), isChecked);
    }

    @Override
    public T getItem(int position) {
        if (list == null || list.size() == 0) {
            return null;
        }

        if (position <= (list.size() - 1))
            return list.get(position);

        return null;
    }

    public void addItems(List<T> tList) {
        this.backUpList.clear();
        this.backUpList.addAll(tList);

        this.list.clear();
        this.list.addAll(tList);

        notifyDataSetChanged();
    }

    public void removeAll() {
        list.clear();
        backUpList.clear();
        notifyDataSetChanged();
    }

    public List<T> getCheckedList() {
        return multiSelectCallBack.getCheckedList(list);
    }


    /*@Override
    public void filter(String newText, List<ModelDistrict> backUpList, List<ModelDistrict> list) {
        if (newText == null)
            return;
        sFilter = newText;
        String query = newText.toLowerCase().trim();
        list.clear();
        for (ModelDistrict data : backUpList) {
            final String districtValue = data.getDistrictValue().toLowerCase();
            final String stateValue = data.getDistrictStateValue().toLowerCase();
            if (districtValue.contains(query) || stateValue.contains(query)) {
                list.add(data);
            }
        }
    }*/

    /*@Override
    public void filter(String newText, List<SpinnerData> backUpList, List<SpinnerData> list) {
        if (newText == null)
            return;
        sFilter = newText;
        String query = newText.toLowerCase().trim();
        list.clear();
        for (SpinnerData data : backUpList) {
            final String value = data.getValue().toLowerCase();
            if (value.contains(query)) {
                list.add(data);
            }
        }
    }*/
}
