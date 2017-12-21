package com.qinnovation.sample.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Set;

/**
 * Created by mohamed.ibrahim on 25-Sep-16.
 */

public abstract class BasePreferenceManager implements PreferenceDataHelper {

    private final static String PREF_NAME = "com.muhammad";

    SharedPreferences preferences;

    BasePreferenceManager(Context context, String prefName) {
        preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

    @Override
    public void putInt(@NonNull String key, int value) {
        if (key == null)
            return;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    @Override
    public void putString(@NonNull String key, String value) {
        if (key == null)
            return;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    @Override
    public void putBoolean(@NonNull String key, boolean value) {
        if (key == null)
            return;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    @Override
    public void putLong(@NonNull String key, long value) {
        if (key == null)
            return;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    @Override
    public void putFloat(@NonNull String key, float value) {
        if (key == null)
            return;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    @Override
    public void putStringSet(@NonNull String key, @Nullable Set<String> stringSet) {
        if (key == null)
            return;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(key, stringSet);
        editor.apply();
    }


    @Override
    public int getInt(@NonNull String key) {
        return preferences.getInt(key, 0);
    }

    @Override
    public int getInt(@NonNull String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }


    @Override
    public String getString(@NonNull String key) {
        return preferences.getString(key, null);
    }

    @Override
    public String getString(@NonNull String key, String defValue) {
        return preferences.getString(key, defValue);
    }


    @Override
    public boolean getBoolean(@NonNull String key) {
        return preferences.getBoolean(key, false);
    }

    @Override
    public boolean getBoolean(@NonNull String key, boolean defValue) {
        return preferences.getBoolean(key, false);
    }


    @Override
    public long getLong(@NonNull String key) {
        return preferences.getLong(key, 0);
    }

    @Override
    public long getLong(@NonNull String key, long defValue) {
        return preferences.getLong(key, defValue);
    }


    @Override
    public float getFloat(@NonNull String key) {
        return preferences.getFloat(key, 0);
    }

    @Override
    public float getFloat(@NonNull String key, float defValue) {
        return preferences.getFloat(key, defValue);
    }


    @Override
    public Set<String> getStringSet(@NonNull String key, @Nullable Set<String> defValues) {
        return preferences.getStringSet(key, defValues);
    }

}
