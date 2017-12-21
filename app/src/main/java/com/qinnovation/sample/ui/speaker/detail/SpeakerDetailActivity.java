package com.qinnovation.sample.ui.speaker.detail;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.qinnovation.sample.R;
import com.qinnovation.sample.helper.recyclerview.OnRecyclerViewClickListener;
import com.qinnovation.sample.ui.base.BaseActivity;
import com.qinnovation.sample.ui.speaker.SpeakerDetail;
import com.qinnovation.sample.utils.ViewUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SpeakerDetailActivity extends BaseActivity implements SpeakerDetailContract.View, OnRecyclerViewClickListener {

    public static final String SPEAKER_DETAIL = "speaker_detail";

    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

//    @BindView(R.id.img_speaker)
//    ImageView imgSpeaker;

    @BindView(R.id.fab_fav)
    FloatingActionButton fabFav;

    @BindView(R.id.img_icon)
    ImageView imgIcon;

    @BindView(R.id.text_name)
    TextView textName;

    @BindView(R.id.text_mail)
    TextView textMail;

    @BindView(R.id.img_session)
    ImageView imgSession;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    boolean isCollapse = true;

    @BindView(R.id.text_description)
    TextView textDescription;

    private int minHeight;
    private int descriptionHeight;

    private SpeakerDetailContract.Presenter presenter;
    private SessionListAdapter adapter;

    @OnClick(R.id.layout_description)
    public void onDescriptionLayoutClick(View view) {
        View viewById = view.findViewById(R.id.text_description);

        int minHeight = viewById.getMinimumHeight();
        int viewHeight = viewById.getHeight();
        int viewMeasuredHeight = viewById.getMeasuredHeight();
        System.out.println("measuredHeight " + viewMeasuredHeight +" - minHeight " + minHeight + " - height = " + viewHeight);

        if (viewById.getHeight() == minHeight) {
            // expand
            ViewUtil.expandView(viewById, descriptionHeight); //'height' is the height of screen which we have measured already.
        } else {
            // collapse
            ViewUtil.collapseView(viewById);
        }
    }

    private SpeakerDetail speakerDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initActivityTransitions();
        setContentView(R.layout.activity_speaker_detail);
        ButterKnife.bind(this);

//        ViewCompat.setTransitionName(imgIcon, getString(R.string.transition_speaker_icon));
//        supportPostponeEnterTransition();
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowHomeEnabled(true);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            speakerDetail = bundle.getParcelable(SPEAKER_DETAIL);
        }
        if (speakerDetail != null)
            bindData();

        ((ImageView) findViewById(R.id.img_session))
                .setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP));
        ((ImageView) findViewById(R.id.img_description))
                .setColorFilter(new PorterDuffColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP));

//        WindowManager windowmanager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics dimension = new DisplayMetrics();
//        windowmanager.getDefaultDisplay().getMetrics(dimension);
//        height = dimension.heightPixels;
        textDescription.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
                textDescription.getViewTreeObserver().removeOnPreDrawListener(this);
                descriptionHeight = textDescription.getHeight();
                minHeight = textDescription.getMinHeight();
                ViewGroup.LayoutParams layoutParams = textDescription.getLayoutParams();
                layoutParams.height = minHeight;
                textDescription.setLayoutParams(layoutParams);

                return true;
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);

        adapter = new SessionListAdapter(this, this);
        recyclerView.setAdapter(adapter);

        presenter = new SpeakerDetailPresenter(this, sqLiteDatabase, preferenceManager);
        presenter.fetchSessionList(3);
    }

    @OnClick(R.id.fab_fav)
    public void onFabFavoriteClick(View view) {
        if (speakerDetail.getFavorite()) {
            ((FloatingActionButton) view).setImageResource(R.drawable.ic_star_amber_600_24dp);
        } else {
            ((FloatingActionButton) view).setImageResource(R.drawable.ic_star_border_amber_600_24dp);
        }
        speakerDetail.setFavorite(!speakerDetail.getFavorite());
    }

    private void bindData() {
        toolbar.setTitle(speakerDetail.getName());
        toolbar.setSubtitle("Speaker");
        collapsingToolbarLayout.setTitle(speakerDetail.getName());
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));

        Picasso.with(this).load(speakerDetail.getImage())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imgIcon);

        textName.setText(speakerDetail.getName());
        textMail.setText(speakerDetail.getEmail());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_speaker_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void navigate(AppCompatActivity activity, View transitionImage, SpeakerDetail viewModel) {
        Intent intent = new Intent(activity, SpeakerDetailActivity.class);
        intent.putExtra(SPEAKER_DETAIL, viewModel);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, activity.getString(R.string.transition_speaker_icon));
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    private void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }

    @Override
    public void populateSessionList(List<SpeakerDetail> speakerDetailList) {
        adapter.addItems(speakerDetailList);
    }

    @Override
    public void progressSessionBarVisibility(boolean visibility) {

    }

    @Override
    public void onItemViewClick(Object obj, ViewGroup parent, View view, int position) {

    }
}
