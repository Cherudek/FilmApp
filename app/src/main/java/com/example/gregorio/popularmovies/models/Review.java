package com.example.gregorio.popularmovies.models;

/**
 * Created by Gregorio on 03/10/2017.
 */

public class Review {

    //Fields
    private String mAuthor;
    private String mReview;

    public Review() {
        // Normal actions performed by class, since this is still a normal object!
    }

    // Review public constructor
    public Review(String author, String review) {
        this.mAuthor = author;
        this.mReview = review;
    }

    //Review object getter methods

    public String getmAuthor() {
        return mAuthor;
    }

    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    //Review Object Setter methods

    public String getmReview() {
        return mReview;
    }

    public void setmReview(String mReview) {
        this.mReview = mReview;
    }
}
