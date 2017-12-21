package com.qinnovation.sample.db;

import com.qinnovation.sample.ui.speaker.SpeakerDetail;

/**
 * Created by qinnovation on 12/17/17.
 */

public class DBConstants {

    public static final String TABLE_MENU = "table_menu";
    public static final String COLUMN_MENU_ID = "_id";
    public static final String COLUMN_MENU_NAME = "name";
    public static final String COLUMN_MENU_ORDER = "menu_order";

    public static final String CREATE_TABLE_MENU = "CREATE TABLE " + TABLE_MENU
            + " ( " + COLUMN_MENU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_MENU_NAME + " TEXT , "
            + COLUMN_MENU_ORDER + " INTEGER ); ";

    public static final String TABLE_SPEAKER = "table_speaker";
    public static final String COLUMN_SPEAKER_ID = "_id";
    public static final String COLUMN_SPEAKER_NAME = "name";
    public static final String COLUMN_SPEAKER_EMAIL = "email";
    public static final String COLUMN_SPEAKER_IMG_URL = "img_url";
    public static final String COLUMN_SPEAKER_ORDER = "speaker_order";

    public static final String CREATE_TABLE_SPEAKER = "CREATE TABLE " + TABLE_SPEAKER
            + " ( " + COLUMN_SPEAKER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_SPEAKER_NAME + " TEXT , "
            + COLUMN_SPEAKER_EMAIL + " TEXT , "
            + COLUMN_SPEAKER_IMG_URL + " TEXT , "
            + COLUMN_SPEAKER_ORDER + " INTEGER ); ";

}
