package com.example.gregorio.popularMovies.Loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.gregorio.popularMovies.Models.Review;
import com.example.gregorio.popularMovies.QueryUtils.QueryReviewUtils;

import java.util.List;

/**
 * Created by Gregorio on 03/10/2017.
 */

public class ReviewLoader extends AsyncTaskLoader<List<Review>> {

    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = ReviewLoader.class.getName();

    /**
     * Query URL
     */
    private String mUrl;


    private List<Review> mReview;

    /**
     * Constructs a new {@link ReviewLoader}.
     *
     * @param context of the activity
     * @param url     to load data from
     */

    public ReviewLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    /*
    * Background Thread loaded
    * If we already have cached results, just deliver them now. If we don't have any
    * cached results, force a load.
    */

    @Override
    protected void onStartLoading() {
        if (mReview != null) {
            deliverResult(mReview);
        } else {
            forceLoad();
        }
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Review> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        List<Review> reviews = QueryReviewUtils.fetchReviewData(mUrl);
        return reviews;
    }

    @Override
    public void deliverResult(List<Review> data) {
        mReview = data;
        super.deliverResult(data);

    }


}
