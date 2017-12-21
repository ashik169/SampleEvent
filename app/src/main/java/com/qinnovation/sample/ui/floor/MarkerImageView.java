package com.qinnovation.sample.ui.floor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;

import com.jsibbold.zoomage.ZoomageView;
import com.qinnovation.sample.R;

/**
 * Created by Qinnovation on 12/19/2017.
 */

public class MarkerImageView extends ZoomageView {

    private static final String TAG = MarkerImageView.class.getSimpleName();
    private float initialX = 400;
    private float initialY = 400;

    private Bitmap bitmap;

    private GestureDetector gestureDetector;
    private Drawable drawable;


    private Bitmap marker;
    private Paint mPaint;
    private Bitmap bmp;
    private Canvas canvas1;

    private ScaleGestureDetector scaleDetector;
    private Bitmap viewBitmap;

    public MarkerImageView(Context context) {
        super(context);
        init();
    }

    public MarkerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MarkerImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        System.out.println("MarkerImageView.init");

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewBitmap = loadBitmapFromView(inflater.inflate(R.layout.marker_image, null));

//        scaleDetector = new ScaleGestureDetector(getContext(), this);
//        ScaleGestureDetectorCompat.setQuickScaleEnabled(scaleDetector, false);

        marker = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_place_red_500_24dp);
        bmp = Bitmap.createBitmap(marker.getWidth(), marker.getHeight(), Bitmap.Config.ARGB_4444);
        canvas1 = new Canvas(bmp);


        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.md_black_500));
        // Create an object of the Android_Gesture_Detector  Class
        ImageGestureDetector imageGestureDetector = new ImageGestureDetector();
        gestureDetector = new GestureDetector(getContext(), imageGestureDetector);

        drawable = getDrawable();
        this.bitmap = ((BitmapDrawable) drawable).getBitmap();


        Bitmap bmp = Bitmap.createBitmap(marker.getWidth(), marker.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bmp);
//        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(marker, initialX, initialY, null);
        drawable.draw(canvas);

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
    }

    public static Bitmap loadBitmapFromView(View v) {
        v.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Bitmap b = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

//        Bitmap b = Bitmap.createBitmap( v.getLayoutParams().width, v.getLayoutParams().height, Bitmap.Config.ARGB_8888);
//        Bitmap b = Bitmap.createBitmap( v.getLayoutParams().width, v.getLayoutParams().height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        return b;
    }

    public void drawFromViewToCanvas(final View view, final Rect rect, final Canvas canvas) {
        final int widthSpec = View.MeasureSpec.makeMeasureSpec(rect.width(), View.MeasureSpec.EXACTLY);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(rect.height(), View.MeasureSpec.EXACTLY);
        view.measure(widthSpec, heightSpec);
        // Lay the view out with the known dimensions
        view.layout(0, 0, rect.width(), rect.height());
        // Translate the canvas so the view is drawn at the proper coordinates
        canvas.save();
        canvas.translate(rect.left, rect.top);
        // Draw the View and clear the translation
        view.draw(canvas);
        canvas.restore();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        System.out.println("onDraw.canvas = " + canvas);
        // ---add the marker---
        if (marker != null) {
//            Matrix imageMatrix = getImageMatrix();
//            imageMatrix.
//            canvas.drawBitmap(marker, 40, 40, null);
            canvas.drawBitmap(viewBitmap, initialX / 1.5f, initialY / 1.5f, null);
            canvas.translate(initialX - 20, initialY - 20);
//            canvas1.translate(initialX, initialY);
//            canvas1.save();
//            canvas1.drawBitmap(marker, initialX, initialY, null);
//            drawable.draw(canvas1);
//            canvas1.restore();
//            canvas.drawCircle(60, 60, 5, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);

//        scaleDetector.onTouchEvent(event);
//        int actionMasked = event.getActionMasked();
//        switch (actionMasked) {
//
//            case MotionEvent.ACTION_DOWN:
//                initialX = event.getX();
//                initialY = event.getY();
//
//                Log.d(TAG, "Action was DOWN - x:" + initialX + " - y:" + initialY);
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//                Log.d(TAG, "Action was MOVE");
//                break;
//
//            case MotionEvent.ACTION_UP:
//                float finalX = event.getX();
//                float finalY = event.getY();
//
//                int pixel = bitmap.getPixel(((int) finalX), ((int) finalY));
//
//                //then do what you want with the pixel data, e.g
//                int redValue = Color.red(pixel);
//                int blueValue = Color.blue(pixel);
//                int greenValue = Color.green(pixel);
//
//                Log.d(TAG, "Action was UP - x:" + finalX + " - y:" + finalY + " - pixel:" + pixel);
//
//                if (initialX < finalX) {
//                    Log.d(TAG, "Left to Right swipe performed");
//                }
//
//                if (initialX > finalX) {
//                    Log.d(TAG, "Right to Left swipe performed");
//                }
//
//                if (initialY < finalY) {
//                    Log.d(TAG, "Up to Down swipe performed");
//                }
//
//                if (initialY > finalY) {
//                    Log.d(TAG, "Down to Up swipe performed");
//                }
//
//                break;
//
//            case MotionEvent.ACTION_CANCEL:
//                Log.d(TAG, "Action was CANCEL");
//                break;
//
//            case MotionEvent.ACTION_OUTSIDE:
//                Log.d(TAG, "Movement occurred outside bounds of current screen element");
//                break;
//        }

        return super.onTouchEvent(event);
    }


    class ImageGestureDetector implements GestureDetector.OnGestureListener,
            GestureDetector.OnDoubleTapListener {

        @Override
        public boolean onDown(MotionEvent e) {
            Log.d("Gesture ", " onDown");
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.d("Gesture ", " onSingleTapConfirmed");
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            initialX = e.getX();
            initialY = e.getY();
            invalidate();
            Log.d("Gesture ", " onSingleTapUp = x:" + initialX + " - y:" + initialY);
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.d("Gesture ", " onShowPress");
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
//            Log.d("Gesture ", " onDoubleTap");
            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
//            Log.d("Gesture ", " onDoubleTapEvent");
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.d("Gesture ", " onLongPress");
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

//            Log.d("Gesture ", " onScroll");
//            if (e1.getY() < e2.getY()) {
//                Log.d("Gesture ", " Scroll Down");
//            }
//            if (e1.getY() > e2.getY()) {
//                Log.d("Gesture ", " Scroll Up");
//            }
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            if (e1.getX() < e2.getX()) {
//                Log.d("Gesture ", "Left to Right swipe: " + e1.getX() + " - " + e2.getX());
//                Log.d("Speed ", String.valueOf(velocityX) + " pixels/second");
//            }
//            if (e1.getX() > e2.getX()) {
//                Log.d("Gesture ", "Right to Left swipe: " + e1.getX() + " - " + e2.getX());
//                Log.d("Speed ", String.valueOf(velocityX) + " pixels/second");
//            }
//            if (e1.getY() < e2.getY()) {
//                Log.d("Gesture ", "Up to Down swipe: " + e1.getX() + " - " + e2.getX());
//                Log.d("Speed ", String.valueOf(velocityY) + " pixels/second");
//            }
//            if (e1.getY() > e2.getY()) {
//                Log.d("Gesture ", "Down to Up swipe: " + e1.getX() + " - " + e2.getX());
//                Log.d("Speed ", String.valueOf(velocityY) + " pixels/second");
//            }
            return true;
        }
    }
}
