package com.qinnovation.sample.utils;


import android.content.Context;
import android.graphics.Typeface;


public class MyTypeface {
    public final static String FA_TYPEFACE = "fa";
    public final static String MD_TYPEFACE = "md";

    public final static int TF_FA = 0x0001;
    public final static int TF_MD = 0x0002;

    private static MyTypeface myTypeface;

    public static MyTypeface getInstance() {
        if (myTypeface == null)
            myTypeface = new MyTypeface();
        return myTypeface;
    }

    public Typeface getOpenSansLightTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(),
                "fonts/OpenSans-Light.ttf");
    }

    public Typeface getGafataTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(),
                "fonts/GAFATA.OTF");
    }

    public Typeface getAgencyRTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(),
                "fonts/AgencyR.TTF");
    }

    public Typeface getBrandTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(),
                "fonts/Kabel-Medium-Regular.ttf");
    }

    public Typeface getFontAwesomeTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(),
                "fonts/fontawesome-webfont.ttf");
    }

    public Typeface getMdTypeface(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/material-icon-font.ttf");
    }

    public Typeface getTypefaceByPath(Context context, String path) {
        return Typeface.createFromAsset(context.getAssets(), path);
    }

    public Typeface getTypeface(Context context, String typeface) {
        if (typeface.equals(FA_TYPEFACE)) {
            return getFontAwesomeTypeface(context);
        } else if (typeface.equals(MD_TYPEFACE)) {
            return getMdTypeface(context);
        }
        return null;
    }

    public Typeface getTypeface(Context context, int tfType) {
        if (tfType == TF_FA) {
            return getFontAwesomeTypeface(context);
        } else if (tfType == TF_MD) {
            return getMdTypeface(context);
        }
        return null;
    }




}