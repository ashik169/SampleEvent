package com.qinnovation.sample.helper.multiselect;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.qinnovation.sample.R;
import com.qinnovation.sample.ui.base.BaseDialogFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by mohamed.ibrahim on 11-Oct-17.
 */

public class MultiSelectDialogFragment<T, VH extends RecyclerView.ViewHolder> extends BaseDialogFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.edit_search)
    EditText editSearch;

    @BindView(R.id.img_close)
    ImageView imageView;

    @BindView(R.id.btn_ok)
    Button btnOk;

    private MultiSelectDialogListener<T> multiSelectDialogListener;

    private int viewId;
    //    private MultiSelectRecyclerAdapter recyclerAdapter;
    private MultiSelectRecyclerAdapter<T, VH> recyclerAdapter;
    private MultiSelectCallBack<T, VH> multiSelectCallBack;

    private String searchHint;
    private List<T> masterList;


    public static MultiSelectDialogFragment newInstance(@IdRes int viewId) {

        Bundle args = new Bundle();

        MultiSelectDialogFragment fragment = new MultiSelectDialogFragment();
        fragment.setViewId(viewId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Fragment fragment = getTargetFragment();
        if (fragment != null) {
            if (fragment instanceof MultiSelectDialogListener) {
                multiSelectDialogListener = (MultiSelectDialogListener) fragment;
            }
        } else if (context instanceof MultiSelectDialogListener) {
            multiSelectDialogListener = (MultiSelectDialogListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_NoActionBar);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.multi_select_dialog, container, false);
        ButterKnife.bind(this, view);

        editSearch.setHint(searchHint);

        imageView.setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary),
                PorterDuff.Mode.SRC_ATOP));

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerAdapter = new MultiSelectRecyclerAdapter<>(getContext(), multiSelectCallBack);
        recyclerAdapter.addItems(masterList);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            window.requestFeature(STYLE_NO_TITLE);
        }
        return dialog;
    }

    @OnClick(value = {R.id.btn_ok, R.id.img_close})
    public void onClickOk(View view) {
        if (view.getId() == R.id.btn_ok) {
            if (multiSelectDialogListener == null) {
                showToastMessage("Dialog response is null");
                return;
            }
            List<T> checkedList = recyclerAdapter.getCheckedList();
            if (checkedList.size() > 0) {
                multiSelectDialogListener.onMultiSelectResponse(getDialog(), viewId, checkedList);
                getDialog().dismiss();
            } else {
                showToastMessage("Select at least one");
            }
        } else if (view.getId() == R.id.img_close) {
            String keyWord = editSearch.getText().toString().trim();
            if (!TextUtils.isEmpty(keyWord)) {
                editSearch.setText(null);
                return;
            }
            getDialog().dismiss();
        }
    }

    @OnTextChanged(value = R.id.edit_search, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void onSearchTextChanged(CharSequence s, int start, int before, int count) {
        if (recyclerAdapter != null)
            recyclerAdapter.filter(s.toString());

        btnOk.setVisibility(TextUtils.isEmpty(s.toString().trim()) ? View.VISIBLE : View.INVISIBLE);
    }


    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

//    private void setAdapter(MultiSelectRecyclerAdapter recyclerAdapter) {
//        this.recyclerAdapter = recyclerAdapter;
//    }

    public void setMultiSelectCallBack(MultiSelectCallBack<T, VH> multiSelectCallBack) {
        this.multiSelectCallBack = multiSelectCallBack;
    }

    public void setSearchHint(String searchHint) {
        this.searchHint = searchHint;
    }

    @Override
    public void onDetach() {
        multiSelectDialogListener = null;
        super.onDetach();
    }

    public void addItems(List<T> masterList) {
        this.masterList = masterList;
    }

    public interface MultiSelectDialogListener<T> {
        void onMultiSelectResponse(Dialog dialog, @IdRes int viewId, List<T> tList);
    }
}
