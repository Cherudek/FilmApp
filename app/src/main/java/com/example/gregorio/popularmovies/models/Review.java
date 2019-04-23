package com.example.gregorio.popularmovies.models;

/**
 * Created by Gregorio on 03/10/2017.
 */

public class Review {

    private String mAuthor;
    private String mReview;

    public Review(String author, String review) {
        this.mAuthor = author;
        this.mReview = review;
    }

    public String getmAuthor() {
        return mAuthor;
    }
    public void setmAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }
    public String getmReview() {
        return mReview;
    }

}
