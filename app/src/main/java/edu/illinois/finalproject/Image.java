package edu.illinois.finalproject;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

/**
 * Created by icebp on 11/28/2017.
 */

public class Image implements Parcelable  {
    private String username;
    private String url;
    private Bitmap image;

    public Image(String username, String url, Bitmap image) {
        this.username = username;
        this.url = url;
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public String getUrl() {
        return url;
    }

    public Bitmap getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.url);
        dest.writeParcelable(this.image, flags);
    }

    protected Image(Parcel in) {
        this.url = in.readString();
        this.username = in.readString();
        this.image = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
