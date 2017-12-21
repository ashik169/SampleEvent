package com.qinnovation.sample.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Kevin Chris on 19 May 2016.
 */
public final class PreferenceManager extends BasePreferenceManager {

    private final static String PREF_NAME = "com.reliance.agri.insurance";

    public static final String PREF_IS_MASTER_DOWNLOADED = "is_master_downloaded";

    private static PreferenceManager preferenceManager;

    private PreferenceManager(Context context) {
        super(context, PREF_NAME);
    }

    public static PreferenceManager getInstance(Context context) {
        if (preferenceManager == null) {
            synchronized (PreferenceManager.class) {
                if (preferenceManager == null) {
                    preferenceManager = new PreferenceManager(context);
                }
            }
        }
        return preferenceManager;
    }

    public SharedPreferences getPreference() {
        return preferences;
    }
}
