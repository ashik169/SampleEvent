package com.qinnovation.sample.application;

import android.app.Application;
import android.content.ComponentCallbacks;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.qinnovation.sample.db.DBHelper;

public class MyApp extends Application {

    private static final String TAG = MyApp.class.getSimpleName();

    public SQLiteDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "####onCreate");
    }

    public SQLiteDatabase getSQLiteDatabase() {
        if (db == null) {
            db = new DBHelper(this).getWritableDatabase();
        }
        return db;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "####onTerminate");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "####onLowMemory");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.d(TAG, "####onTrimMemory");
    }

    @Override
    public void registerComponentCallbacks(ComponentCallbacks callback) {
        super.registerComponentCallbacks(callback);
        Log.d(TAG, "####registerComponentCallbacks");
    }

    @Override
    public void unregisterComponentCallbacks(ComponentCallbacks callback) {
        super.unregisterComponentCallbacks(callback);
        Log.d(TAG, "####unregisterComponentCallbacks");
    }

    @Override
    public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.registerActivityLifecycleCallbacks(callback);
        Log.d(TAG, "####registerActivityLifecycleCallbacks");
    }

    @Override
    public void unregisterActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.unregisterActivityLifecycleCallbacks(callback);
        Log.d(TAG, "####unregisterActivityLifecycleCallbacks");
    }
}
