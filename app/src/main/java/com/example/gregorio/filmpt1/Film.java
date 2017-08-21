package com.example.gregorio.filmpt1;


// A Custom Film Class to create Film Objects

import android.os.Parcel;
import android.os.Parcelable;

public class Film implements Parcelable {

    public static final Creator<Film> CREATOR = new Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };
    // Fields
    private String mTitle;
    private String mPlot;
    private String mReleaseDate;
    private String mThumbnail;
    private String mUserRating;

    // Film public constructor
    public Film(String mTitle, String mPlot, String mReleaseDate, String mThumbnail, String mUserRating) {
        this.mTitle = mTitle;
        this.mPlot = mPlot;
        this.mReleaseDate = mReleaseDate;
        this.mThumbnail = mThumbnail;
        this.mUserRating = mUserRating;
    }

    // Using the `in` variable, we can retrieve the values that
    // we originally wrote into the `Parcel`.  This constructor is usually
    // private so that only the `CREATOR` field can access.
    private Film(Parcel in) {
        mTitle = in.readString();
        mPlot = in.readString();
        mReleaseDate = in.readString();
        mThumbnail = in.readString();
        mUserRating = in.readString();
    }

    public Film() {
        // Normal actions performed by class, since this is still a normal object!
    }

    //Getter Methods
    public String getmTitle() {
        return mTitle;
    }

    public String getmPlot() {
        return mPlot;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public String getmThumbnail() {
        return mThumbnail;
    }

    public String getmUserRating() {
        return mUserRating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mTitle);
        out.writeString(mPlot);
        out.writeString(mReleaseDate);
        out.writeString(mThumbnail);
        out.writeString(mUserRating);
    }
}

