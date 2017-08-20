package com.example.gregorio.filmpt1;


import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class FilmLoader extends AsyncTaskLoader<List<Film>> {

    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = FilmLoader.class.getName();

    /**
     * Query URL
     */
    private String mUrl;

    /**
     * Constructs a new {@link FilmLoader}.
     *
     * @param context of the activity
     * @param url     to load data from
     */
    public FilmLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    //Background Thread loaded
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Film> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of MOVIES.
        List<Film> movies = QueryUtils.fetchMovieData(mUrl);
        return movies;
    }
}
