package com.example.gregorio.filmpt1;


// A Custom Film Class to create Film Objects

public class Film {

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
}

