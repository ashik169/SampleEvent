package com.qinnovation.sample.ui.sponsors;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.qinnovation.sample.R;
import com.qinnovation.sample.helper.recyclerview.SimpleAdapter;
import com.qinnovation.sample.helper.recyclerview.SimpleSectionedRecyclerViewAdapter;
import com.qinnovation.sample.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SponsorsActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private SimpleAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsors);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);

        List<String> dataList = new ArrayList<>(20);

        for (int i = 0; i < dataList.size(); i++) {
            dataList.add("Data " + (i + 1));
        }

        //Your RecyclerView.Adapter
        mAdapter = new SimpleAdapter(this, dataList);

        //This is the code to provide a sectioned list
        List<SimpleSectionedRecyclerViewAdapter.Section> sections =
                new ArrayList<>();

        //Sections
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(0, "Section 1"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(5, "Section 2"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(12, "Section 3"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(14, "Section 4"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(20, "Section 5"));

        //Add your adapter to the sectionAdapter
        SimpleSectionedRecyclerViewAdapter.Section[] dummy = new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
        SimpleSectionedRecyclerViewAdapter mSectionedAdapter = new
                SimpleSectionedRecyclerViewAdapter(this, mAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));

        //Apply this adapter to the RecyclerView
        recyclerView.setAdapter(mSectionedAdapter);
    }
}
