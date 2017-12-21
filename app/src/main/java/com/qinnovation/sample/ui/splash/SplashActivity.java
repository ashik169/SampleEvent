package com.qinnovation.sample.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.qinnovation.sample.R;
import com.qinnovation.sample.ui.base.BaseActivity;
import com.qinnovation.sample.ui.home.HomePageActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends BaseActivity {


    private Handler handler;
    private ScaleAnimation scaleAnimation;

    @BindView(R.id.img_logo)
    ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        scaleAnimation = new ScaleAnimation(1, 1.2f, 1, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(100);
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        scaleAnimation.setRepeatCount(1);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                handler = new Handler();
                handler.postDelayed(() -> {
                    Intent intent = new Intent(SplashActivity.this, HomePageActivity.class);
                    startActivity(intent);
                    finish();
                }, 1000);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

//        handler = new Handler();
//        handler.postDelayed(() -> {
//            Intent intent = new Intent(SplashActivity.this, HomePageActivity.class);
//            startActivity(intent);
//            finish();
//        }, 3000);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        imgLogo.startAnimation(scaleAnimation);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
