package com.qinnovation.sample.utils;

import android.annotation.TargetApi;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by mohamed.ibrahim on 25-Oct-16.
 */

public class DrawableUtil {

    private static DrawableUtil drawableUtil;

    private DrawableUtil() {
    }

    public static DrawableUtil getInstance() {
        if (drawableUtil == null)
            drawableUtil = new DrawableUtil();
        return drawableUtil;
    }

    public static Drawable applyDrawableColor(Drawable drawable, int color) {
        if (drawable == null)
            return null;
        if (drawable instanceof GradientDrawable) {
            GradientDrawable gradientDrawable = (GradientDrawable) drawable.mutate();
            gradientDrawable.setColor(color);
            return gradientDrawable;
        }
        return null;
    }

    public static void applyBitmapDrawableColor(ImageView deleteImage, Drawable drawable, int color) {
        if (drawable == null)
            return;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable.mutate();
            bitmapDrawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));
            deleteImage.setImageDrawable(bitmapDrawable);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressWarnings("deprecation")
    public static void setBackground(View view, Drawable drawable) {
        final int sdk = Build.VERSION.SDK_INT;
        if(sdk < Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(drawable);
        } else {
            view.setBackground(drawable);
        }
    }
}
