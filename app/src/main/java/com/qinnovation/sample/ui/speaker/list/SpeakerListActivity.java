package com.qinnovation.sample.ui.speaker.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.qinnovation.sample.R;
import com.qinnovation.sample.helper.recyclerview.OnRecyclerViewClickListener;
import com.qinnovation.sample.ui.base.BaseActivity;
import com.qinnovation.sample.ui.speaker.SpeakerDetail;
import com.qinnovation.sample.ui.speaker.detail.SpeakerDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qinnovation on 12/17/17.
 */

public class SpeakerListActivity extends BaseActivity implements SpeakerListContract.View, OnRecyclerViewClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(android.R.id.progress)
    ProgressBar progressBar;
    ;
    SpeakerListAdapter speakerListAdapter;

    SpeakerListContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowHomeEnabled(true);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setTitle("Speakers");

        if (presenter == null)
            presenter = new SpeakerListPresenter(this, sqLiteDatabase, preferenceManager);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setNestedScrollingEnabled(false);

        speakerListAdapter = new SpeakerListAdapter(this, this);
        recyclerView.setAdapter(speakerListAdapter);

        presenter.fetchSpeakerList();
    }

    @Override
    public void populateSpeakerList(List<SpeakerDetail> speakerDetailList) {
        speakerListAdapter.addItems(speakerDetailList);
    }

    @Override
    public void progressBarVisibility(boolean visibility) {
        progressBar.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onItemViewClick(Object obj, ViewGroup parent, View view, int position) {
        SpeakerDetailActivity.navigate(this, parent.findViewById(R.id.img_speaker), ((SpeakerDetail) obj));
    }
}
