package com.qinnovation.sample.master.model;

import android.database.Cursor;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.qinnovation.sample.db.DBConstants;
import com.qinnovation.sample.ui.speaker.SpeakerDetail;

/**
 * Created by qinnovation on 12/17/17.
 */

public class MenuDetail {


    private int menuIconDrawable;
    private int menuIconColor;
    private int count;

    public MenuDetail() {

    }

    public MenuDetail(String menuName) {
        this.name = menuName;
    }

    public MenuDetail(String menuName, @DrawableRes int menuIconDrawable) {
        this.name = menuName;
        this.menuIconDrawable = menuIconDrawable;
    }

    public MenuDetail(String menuName, @DrawableRes int menuIconDrawable, @ColorRes int menuIconColor) {
        this.name = menuName;
        this.menuIconDrawable = menuIconDrawable;
        this.menuIconColor = menuIconColor;
    }


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("order")
    @Expose
    private Integer order;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("note")
    @Expose
    private String note;

    public Integer getId() {
        return id == null ? 0 : id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public int getMenuIconDrawable() {
        return menuIconDrawable;
    }

    public void setMenuIconDrawable(int menuIconDrawable) {
        this.menuIconDrawable = menuIconDrawable;
    }

    public int getMenuIconColor() {
        return menuIconColor;
    }

    public void setMenuIconColor(int menuIconColor) {
        this.menuIconColor = menuIconColor;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "id - " + id + ", name - " + name + ", order id - " + order + ", note - " + note;
    }

    public MenuDetail parseCursorDetail(Cursor cursor) {
        if (cursor == null) {
            return this;
        }

        this.id = cursor.getInt(cursor.getColumnIndexOrThrow(DBConstants.COLUMN_MENU_ID));
        this.name = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COLUMN_MENU_NAME));
        this.order = cursor.getInt(cursor.getColumnIndexOrThrow(DBConstants.COLUMN_MENU_ORDER));

        return this;
    }
}
