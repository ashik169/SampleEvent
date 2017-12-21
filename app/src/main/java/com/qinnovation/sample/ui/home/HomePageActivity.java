package com.qinnovation.sample.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.qinnovation.sample.R;
import com.qinnovation.sample.helper.recyclerview.OnRecyclerViewClickListener;
import com.qinnovation.sample.master.model.MenuDetail;
import com.qinnovation.sample.ui.base.BaseActivity;
import com.qinnovation.sample.ui.floor.FloorPlanActivity;
import com.qinnovation.sample.ui.home.adapter.MenuGridAdapter;
import com.qinnovation.sample.ui.home.header.SpeakerHeaderFragment;
import com.qinnovation.sample.ui.speaker.list.SpeakerListActivity;
import com.qinnovation.sample.ui.speaker.SpeakerDetail;
import com.qinnovation.sample.ui.sponsors.SponsorsActivity;
import com.qinnovation.sample.utils.DimensionUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePageActivity extends BaseActivity implements HomePageContract.View, OnRecyclerViewClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(android.R.id.progress)
    ProgressBar progressBar;

    private MenuGridAdapter menuGridAdapter;

    private HomePageContract.Presenter presenter;

    private HeaderPagerAdapter headerPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBar supportActionBar = getSupportActionBar();

        if (supportActionBar != null) {
            supportActionBar.setDisplayShowHomeEnabled(false);
//            supportActionBar.setIcon(R.mipmap.ic_launcher);
        }

        if (presenter == null)
            presenter = new HomePagePresenter(this, sqLiteDatabase, preferenceManager);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);

        menuGridAdapter = new MenuGridAdapter(this, this);
        recyclerView.setAdapter(menuGridAdapter);

        viewPager.setPageMargin(DimensionUtil.dpToPx2(this, 10));
        headerPagerAdapter = new HeaderPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(headerPagerAdapter);

        presenter.fetchSpeakerList(3);

        presenter.fetchMenuList();

//        List<MenuDetail> gridMenuList = getGridMenuList();
//        menuGridAdapter.addItems(gridMenuList);
    }

    @Override
    public void populateMenuList(List<MenuDetail> menuDetailList) {
        if (menuDetailList == null || menuDetailList.size() == 0) {
//            List<MenuDetail> gridMenuList = getGridMenuList();
//            menuGridAdapter.addItems(gridMenuList);
        } else {
            menuGridAdapter.addItems(menuDetailList);
        }
    }

    @Override
    public void populateSpeakerList(List<SpeakerDetail> speakerDetailList) {
        headerPagerAdapter.addItems(speakerDetailList);
    }

    @Override
    public void progressBarVisibility(boolean visibility) {
        progressBar.setVisibility(visibility ? View.VISIBLE : View.GONE);

    }

    @Override
    public void onItemViewClick(Object obj, ViewGroup parent, View view, int position) {
        MenuDetail menuDetail = (MenuDetail) obj;
        int menuDetailId = menuDetail.getId();
        switch (menuDetailId) {
            case 1:
                Intent floorIntent = new Intent(this, FloorPlanActivity.class);
                startActivityForResult(floorIntent, 101);
                break;
            case 3:
                Intent intent = new Intent(this, SpeakerListActivity.class);
                startActivityForResult(intent, 100);
                break;
            case 7:
                Intent sponsorIntent = new Intent(this, SponsorsActivity.class);
                startActivityForResult(sponsorIntent, 102);
                break;
            default:
                showToastMessage("Screen not available");
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.item_light:
                menuGridAdapter.setThemeFlag(1);
                return true;

            case R.id.item_color:
                menuGridAdapter.setThemeFlag(0);
                return true;

            case R.id.item_circle_color:
                menuGridAdapter.setThemeFlag(2);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    boolean exit = false;

    @Override
    public void onBackPressed() {
        if (exit) {
            super.onBackPressed();
            finish();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 2000);
            showToastMessage("Press back again to exit", Toast.LENGTH_SHORT);
            exit = true;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                presenter.fetchSpeakerList(3);

                break;
        }
    }


    static class HeaderPagerAdapter extends FragmentPagerAdapter {

        private List<SpeakerDetail> speakerDetailList;

        public HeaderPagerAdapter(FragmentManager fm) {
            super(fm);
            speakerDetailList = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            return SpeakerHeaderFragment.newInstance(speakerDetailList.get(position));
        }

        @Override
        public int getCount() {
            return speakerDetailList.size();
        }

        public void addItems(List<SpeakerDetail> speakerDetailList) {
            this.speakerDetailList = speakerDetailList;
            notifyDataSetChanged();
        }
    }

    private List<MenuDetail> getGridMenuList() {
        List<MenuDetail> menuList = new ArrayList<>();

        menuList.add(new MenuDetail("My Profile", R.drawable.ic_person_white_36dp));
        menuList.add(new MenuDetail("Info", R.drawable.ic_info_white_36dp));
        menuList.add(new MenuDetail("Search", R.drawable.ic_search_white_36dp));
        menuList.add(new MenuDetail("News Feed", R.drawable.ic_rss_feed_white_36dp));
        MenuDetail inboxMenu = new MenuDetail("Inbox", R.drawable.ic_mail_black_36dp);
        inboxMenu.setCount(10);
        menuList.add(inboxMenu);
        menuList.add(new MenuDetail("Conference Sessions", R.drawable.ic_history_white_36dp));
        menuList.add(new MenuDetail("Speakers", R.drawable.ic_record_voice_over_white_36dp));
        menuList.add(new MenuDetail("Parallel Sessions Program", R.drawable.ic_history_white_36dp));
        menuList.add(new MenuDetail("Company Workshops", R.drawable.ic_store_white_36dp));
        menuList.add(new MenuDetail("Sponsors", R.drawable.ic_person_white_36dp));
        menuList.add(new MenuDetail("Exhibitor Catalog", R.drawable.ic_history_white_36dp));
        menuList.add(new MenuDetail("Floor Plan", R.drawable.ic_map_white_24dp));
        menuList.add(new MenuDetail("Photo Sharing", R.drawable.ic_share_white_36dp));
        menuList.add(new MenuDetail("Notes", R.drawable.ic_note_add_white_36dp));
        menuList.add(new MenuDetail("Scavenger hunt", R.drawable.ic_history_white_36dp));

        return menuList;
    }
}
