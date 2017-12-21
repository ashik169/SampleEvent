package com.qinnovation.sample.ui.speaker;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.qinnovation.sample.db.DBConstants;

/**
 * Created by qinnovation on 12/17/17.
 */

public class SpeakerDetail implements Parcelable {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("order")
    @Expose
    private Integer order;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("favorite")
    @Expose
    private Boolean favorite;


    public SpeakerDetail() {
    }

    protected SpeakerDetail(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        if (in.readByte() == 0) {
            order = null;
        } else {
            order = in.readInt();
        }
        email = in.readString();
        image = in.readString();
        byte tmpFavorite = in.readByte();
        favorite = tmpFavorite == 0 ? null : tmpFavorite == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        if (order == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(order);
        }
        dest.writeString(email);
        dest.writeString(image);
        dest.writeByte((byte) (favorite == null ? 0 : favorite ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SpeakerDetail> CREATOR = new Creator<SpeakerDetail>() {
        @Override
        public SpeakerDetail createFromParcel(Parcel in) {
            return new SpeakerDetail(in);
        }

        @Override
        public SpeakerDetail[] newArray(int size) {
            return new SpeakerDetail[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getFavorite() {
        return favorite == null ? false : favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return "ID - " + id + ", Name - " + name + ", Email - " + email;
    }

    public SpeakerDetail parseCursorDetail(Cursor cursor) {
        if (cursor == null) {
            return this;
        }

        this.id = cursor.getInt(cursor.getColumnIndexOrThrow(DBConstants.COLUMN_SPEAKER_ID));
        this.name = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COLUMN_SPEAKER_NAME));
        this.email = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COLUMN_SPEAKER_EMAIL));
        this.image = cursor.getString(cursor.getColumnIndexOrThrow(DBConstants.COLUMN_SPEAKER_IMG_URL));
        this.order = cursor.getInt(cursor.getColumnIndexOrThrow(DBConstants.COLUMN_SPEAKER_ORDER));

        return this;
    }
}
