package com.qinnovation.sample.helper.mvp;


public interface IBasePresenter<V> {

    boolean isViewAttached();

    void onViewAttached(V view);

    void onViewDestroy();
}
