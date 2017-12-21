package com.qinnovation.sample.helper.mvp;


import android.database.sqlite.SQLiteDatabase;

import com.qinnovation.sample.preferences.PreferenceManager;


/**
 * Abstract base class to be extended by any MVP Presenter. It contains common attributes and
 * methods to be shared across Presenters.
 *
 * @param <V> a generic type to indicate a type of MVP View
 */
public abstract class BasePresenter<V> implements IBasePresenter<V> {

    protected V view;
    protected SQLiteDatabase sqLiteDatabase;
    protected PreferenceManager preferenceManager;

    public BasePresenter(V v, SQLiteDatabase sqLiteDatabase, PreferenceManager preferenceManager) {
        this.view = v;
        this.sqLiteDatabase = sqLiteDatabase;
        this.preferenceManager = preferenceManager;
    }

    @Override
    public void onViewAttached(V view) {
        this.view = view;
    }

    @Override
    public void onViewDestroy() {
        view = null;
    }

    @Override
    public boolean isViewAttached() {
        return view != null;
    }


}
