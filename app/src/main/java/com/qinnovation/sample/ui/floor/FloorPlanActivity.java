package com.qinnovation.sample.ui.floor;

import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.OnMatrixChangedListener;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.OnSingleFlingListener;
import com.qinnovation.sample.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FloorPlanActivity extends AppCompatActivity {


    @BindView(R.id.iv_photo)
    MarkerPhotoView mPhotoView;

    private Drawable bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor_plan);
        ButterKnife.bind(this);

        bitmap = ContextCompat.getDrawable(this, R.drawable.floor_plan);
        mPhotoView.setMarker(findViewById(R.id.marker_layout));
        mPhotoView.setImageDrawable(bitmap);
//        mPhotoView.setScaleType(ImageView.ScaleType.CENTER);
        // Lets attach some listeners, not required though!
        mPhotoView.setOnMatrixChangeListener(new MatrixChangeListener());
        mPhotoView.setOnPhotoTapListener(new PhotoTapListener());
        mPhotoView.setOnSingleFlingListener(new SingleFlingListener());

    }

    static final String PHOTO_TAP_TOAST_STRING = "Photo Tap! X: %.2f %% Y:%.2f %% ID: %d";
    static final String SCALE_TOAST_STRING = "Scaled to: %.2ff";
    static final String FLING_LOG_STRING = "Fling velocityX: %.2f, velocityY: %.2f";

    private class PhotoTapListener implements OnPhotoTapListener {

        @Override
        public void onPhotoTap(ImageView view, float x, float y) {
            float xPercentage = x * 100f;
            float yPercentage = y * 100f;
//            Log.d("PhotoView", "onPhotoTap" + String.format(PHOTO_TAP_TOAST_STRING, xPercentage, yPercentage, view == null ? 0 : view.getId()));
        }
    }


    private class MatrixChangeListener implements OnMatrixChangedListener {

        @Override
        public void onMatrixChanged(RectF rect) {
//            mCurrMatrixTv.setText(rect.toString());
            Log.d("PhotoView", "onMatrixChanged - " + rect.toString());
        }
    }

    private class SingleFlingListener implements OnSingleFlingListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d("PhotoView", "onFling" + String.format(FLING_LOG_STRING, velocityX, velocityY));

            return true;
        }
    }
}
