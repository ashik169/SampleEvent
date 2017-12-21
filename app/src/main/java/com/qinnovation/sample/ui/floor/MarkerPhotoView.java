package com.qinnovation.sample.ui.floor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.OnMatrixChangedListener;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.qinnovation.sample.R;

/**
 * Created by Qinnovation on 12/19/2017.
 */

public class MarkerPhotoView extends PhotoView implements OnPhotoTapListener, OnMatrixChangedListener {

    private static final String TAG = MarkerPhotoView.class.getSimpleName();
    private float initialX = 400;
    private float initialY = 400;

    private Bitmap bitmap;

    private GestureDetector gestureDetector;
    private Drawable drawable;

    //    private Bitmap marker;
    private Paint mPaint;
    private Bitmap bmp;
    private Canvas canvas1;

    //    private ScaleGestureDetector scaleDetector;
    private Bitmap viewBitmap;
    private OnPhotoTapListener photoTaplistener;
    private OnMatrixChangedListener matrixChangeListener;
    private Matrix mMatrix;
    private RectF rect;
    private View inflate;

    private float map_x;
    private float map_y;
//    private PhotoViewAttacher attacher;

    public MarkerPhotoView(Context context) {
        super(context);
        init();
    }

    public MarkerPhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MarkerPhotoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
//        attacher = new PhotoViewAttacher(this);
        System.out.println("MarkerImageView.init");

        mMatrix = new Matrix();

        super.setOnPhotoTapListener(this);
        super.setOnMatrixChangeListener(this);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflate = inflater.inflate(R.layout.marker_image, null);

        viewBitmap = loadBitmapFromView(inflate);

//        ViewGroup parent = (ViewGroup) getParent();
//        parent.addView(inflate);

        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.md_black_500));

        // Create an object of the Android_Gesture_Detector  Class
        ImageGestureDetector imageGestureDetector = new ImageGestureDetector();
        gestureDetector = new GestureDetector(getContext(), imageGestureDetector);

        drawable = getDrawable();
        this.bitmap = ((BitmapDrawable) drawable).getBitmap();

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
    }

    @Override
    public void setOnPhotoTapListener(OnPhotoTapListener listener) {
        this.photoTaplistener = listener;
    }

    @Override
    public void setOnMatrixChangeListener(OnMatrixChangedListener listener) {
        this.matrixChangeListener = listener;
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        // setImageBitmap calls through to this method
//        if (attacher != null) {
//            attacher.update();
//        }
    }

    @Override
    public void onPhotoTap(ImageView view, float x, float y) {
        System.out.println("MarkerPhotoView.onPhotoTap x:" + initialX + " - y:" + initialY);

        mMatrix = view.getMatrix();
        initialX = x * 100f;
        initialY = y * 100f;
        photoTaplistener.onPhotoTap(view, x, y);
        handleUserTap(x, y);
//        inflate.setTranslationX(x);
//        inflate.setTranslationY(y);
//        invalidate();
    }

    @Override
    public void onMatrixChanged(RectF rect) {
        this.rect = rect;
        if (matrixChangeListener != null)
            matrixChangeListener.onMatrixChanged(rect);

//        inflate.setTranslationX(rect.left / 2);
//        inflate.setTranslationY(rect.top / 2);
        invalidate();
    }


    boolean onLoad = false;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        System.out.println("onDraw.canvas = " + canvas);
//        RectF displayRect = new RectF();

//        if(attacher != null) {
//            displayRect = attacher.getDisplayRect();
//        }
        MapDraw mapdraw = new MapDraw(canvas, getDisplayRect());

        mapdraw.drawImageOnMap(viewBitmap, rect.left, rect.top,120);
//        if (onLoad) {
//        canvas.save();
//        canvas.translate(1f, 1f);
//        canvas.restore();
//        canvas.save();
//        canvas.concat(mMatrix);
//            canvas.drawBitmap(viewBitmap, rect.left, rect.top, null);
//        canvas.drawBitmap(viewBitmap, rect.centerX(), rect.centerY(), null);
//        canvas.drawBitmap(viewBitmap, initialX / 1.5f, initialY / 1.5f, null);
        onLoad = true;
//    }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);

        return super.onTouchEvent(event);
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
        final int widthSpec = MeasureSpec.makeMeasureSpec(rect.width(), MeasureSpec.EXACTLY);
        final int heightSpec = MeasureSpec.makeMeasureSpec(rect.height(), MeasureSpec.EXACTLY);
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

    public void setMarker(View inflate) {
        this.inflate = inflate;
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
//            initialX = e.getX();
//            initialY = e.getY();
//            invalidate();
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

    /**
     * Called when user taps at location x,y on map
     * <p>
     * The units are relative to the image and between 0.0 and 1.0. That is, the center
     * of the map is (0.5, 0.5)
     */
    public void handleUserTap(float x, float y) {

        // Convert to map units
        // On map_full, it is (x * width, y * height). Then we perform scaling.
        map_x = (x * MapDraw.MAP_WIDTH) + 2f;
        map_y = (y * MapDraw.MAP_HEIGHT) + 2f;

        invalidate();
//        Building closestBuilding = determineBuildingFromPosition(map_x, map_y, 70);

    }


    static class Building {

        private boolean selectable;
        private Object mainFloor;

        public boolean isSelectable() {
            return selectable;
        }

        public void setSelectable(boolean selectable) {
            this.selectable = selectable;
        }

        public Object getMainFloor() {
            return mainFloor;
        }

        public void setMainFloor(Object mainFloor) {
            this.mainFloor = mainFloor;
        }
    }
}
