package com.qinnovation.sample.preferences;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Set;

/**
 * Created by mohamed.ibrahim on 25-Sep-16.
 */

public interface PreferenceDataHelper {

    void putInt(@NonNull String key, int value);

    int getInt(@NonNull String key);

    int getInt(@NonNull String key, int defaultValue);


    void putString(@NonNull String key, String value);

    String getString(@NonNull String key);

    String getString(@NonNull String key, String defValue);


    void putBoolean(@NonNull String key, boolean value);

    boolean getBoolean(@NonNull String key);

    boolean getBoolean(@NonNull String key, boolean defValue);


    void putLong(@NonNull String key, long value);

    long getLong(@NonNull String key);

    long getLong(@NonNull String key, long defValue);


    void putFloat(@NonNull String key, float value);

    float getFloat(@NonNull String key);

    float getFloat(@NonNull String key, float defValue);


    Set<String> getStringSet(@NonNull String key, @Nullable Set<String> defValues);

    void putStringSet(@NonNull String key, @Nullable Set<String> stringSet);

}
