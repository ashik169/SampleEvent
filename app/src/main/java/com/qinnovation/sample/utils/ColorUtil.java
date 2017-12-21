package com.qinnovation.sample.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;

public final class ColorUtil {
    private static ColorUtil ourInstance = new ColorUtil();

    public static ColorUtil getInstance() {
        return ourInstance;
    }

    private ColorUtil() {
    }

    public Drawable applyDrawableColor(Context mContext, Drawable drawable, int alpha, int color) {
        GradientDrawable drawable1 = null;
        if (drawable instanceof GradientDrawable) {
            drawable1 = (GradientDrawable) drawable.mutate();
            if (alpha > 0)
                drawable1.setAlpha(alpha);
            drawable1.setColor(ContextCompat.getColor(mContext, color));
        }
        return drawable1;
    }
}
