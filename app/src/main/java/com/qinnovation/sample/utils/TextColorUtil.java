package com.qinnovation.sample.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import com.qinnovation.sample.R;


public class TextColorUtil {
    private static TextColorUtil ourInstance = new TextColorUtil();

    public static TextColorUtil getInstance() {
        return ourInstance;
    }

    private TextColorUtil() {
    }

    public SpannableString getSpanText(Context context, @StringRes int resId, String content) {
        String finalString = context.getString(resId, content);

        try {
            int startPos = finalString.indexOf(content);
            int endPos = startPos + content.length();

            SpannableString text = new SpannableString(finalString);
            text.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimary)), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            text.setSpan(new StyleSpan(Typeface.BOLD), startPos, endPos, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            return text;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new SpannableString(finalString);
    }

    public SpannableString getSpanText(Context context, String fullContent, String content) {
        try {

            if (fullContent.toLowerCase().contains(content.toLowerCase())) {
                int startPos = fullContent.toLowerCase().indexOf(content.toLowerCase());
                int endPos = startPos + content.length();

                SpannableString text = new SpannableString(fullContent);
                text.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimary)), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                text.setSpan(new StyleSpan(Typeface.BOLD), startPos, endPos, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                return text;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new SpannableString(fullContent);
    }
}
