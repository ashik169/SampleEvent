package com.qinnovation.sample.ui.home;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

/**
 * Created by qinnovation on 12/13/17.
 */

public class GridMenu {

    private String menuName;

    private int menuIconDrawable;

    private int menuIconColor;


    private int count;

    public GridMenu(String menuName) {
        this.menuName = menuName;
    }

    public GridMenu(String menuName, @DrawableRes int menuIconDrawable) {
        this.menuName = menuName;
        this.menuIconDrawable = menuIconDrawable;
    }

    public GridMenu(String menuName, @DrawableRes int menuIconDrawable, @ColorRes int menuIconColor) {
        this.menuName = menuName;
        this.menuIconDrawable = menuIconDrawable;
        this.menuIconColor = menuIconColor;
    }



    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuIconDrawable(int menuIconDrawable) {
        this.menuIconDrawable = menuIconDrawable;
    }

    public int getMenuIconDrawable() {
        return menuIconDrawable;
    }

    public void setMenuIconColor(int menuIconColor) {
        this.menuIconColor = menuIconColor;
    }

    public int getMenuIconColor() {
        return menuIconColor;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
