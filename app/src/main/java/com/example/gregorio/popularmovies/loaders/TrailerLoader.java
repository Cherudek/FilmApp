package com.example.gregorio.popularmovies.loaders;

import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import com.example.gregorio.popularmovies.models.Trailer;
import com.example.gregorio.popularmovies.queryutils.QueryTrailerUtils;

import java.util.List;

/**
 * Created by Gregorio on 05/10/2017.
 */

public class TrailerLoader extends AsyncTaskLoader<List<Trailer>> {

    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = TrailerLoader.class.getName();

    /**
     * Query URL
     */
    private String mUrl;

    private List<Trailer> mTrailer;

    /**
     * Constructs a new {@link TrailerLoader}.
     *
     * @param context of the activity
     * @param mUrl    to load data from
     */

    public TrailerLoader(Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }

        /*
    * Background Thread loaded
    * If we already have cached results, just deliver them now. If we don't have any
    * cached results, force a load.
    */

    @Override
    protected void onStartLoading() {
        if (mTrailer != null) {
            deliverResult(mTrailer);
        } else {
            forceLoad();
        }
    }

    /**
     * This is on a background thread.
     */

    @Override
    public List<Trailer> loadInBackground() {

        List<Trailer> trailers = QueryTrailerUtils.fetchTrailerData(mUrl);
        return trailers;
    }

    @Override
    public void deliverResult(List<Trailer> data) {
        mTrailer = data;
        super.deliverResult(data);

    }
}
