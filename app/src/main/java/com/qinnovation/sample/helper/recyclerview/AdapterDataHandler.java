package com.qinnovation.sample.helper.recyclerview;

import java.util.List;

/**
 * Created by mohamed.ibrahim on 09-Oct-16.
 */

public interface AdapterDataHandler<T> {

    void addItem(T t);

    void addItemAt(int position, T t);

    void addItems(List<T> t);

    void addAllItem(List<T> tList);

    T getItem(int position);

    List<T> getItems();

    void removeAll();

    void removeItemAt(int position);

    void removeItem(T t);
}
