package com.example.gregorio.popularmovies.models;


// A Custom Film Class to create Film Objects

import android.os.Parcel;
import android.os.Parcelable;

public class Film implements Parcelable {


    public static final Parcelable.Creator<Film> CREATOR = new Parcelable.Creator<Film>() {

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
    private String mId;
    private String mPlot;
    private String mReleaseDate;
    private String mThumbnail;


    // This is where you write the values you want to save to the `Parcel`.
    // The `Parcel` class has methods defined to help you save all of your values.
    // Note that there are only methods defined for simple values, lists, and other Parcelable objects.
    // You may need to make several classes Parcelable to send the data you want.
    private String mUserRating;


    // Using the `in` variable, we can retrieve the values that
    // we originally wrote into the `Parcel`.  This constructor is usually
    // private so that only the `CREATOR` field can access.
    private Film(Parcel in) {
        mTitle = in.readString();
        mId = in.readString();
        mPlot = in.readString();
        mReleaseDate = in.readString();
        mThumbnail = in.readString();
        mUserRating = in.readString();
    }


    public Film() {
        // Normal actions performed by class, since this is still a normal object!
    }

    // Film public constructor
    public Film(String mTitle, String mId, String mPlot, String mReleaseDate, String mThumbnail, String mUserRating) {
        this.mTitle = mTitle;
        this.mId = mId;
        this.mPlot = mPlot;
        this.mReleaseDate = mReleaseDate;
        this.mThumbnail = mThumbnail;
        this.mUserRating = mUserRating;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mTitle);
        out.writeString(mId);
        out.writeString(mPlot);
        out.writeString(mReleaseDate);
        out.writeString(mThumbnail);
        out.writeString(mUserRating);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    // Setter Methods

    //Getter Methods
    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmPlot() {
        return mPlot;
    }

    public void setmPlot(String mPlot) {
        this.mPlot = mPlot;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public void setmReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public String getmThumbnail() {
        return mThumbnail;
    }

    public void setmThumbnail(String mThumbnail) {
        this.mThumbnail = mThumbnail;
    }

    public String getmUserRating() {
        return mUserRating;
    }

    public void setmUserRating(String mUserRating) {
        this.mUserRating = mUserRating;
    }


}

