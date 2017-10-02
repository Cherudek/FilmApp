package com.example.gregorio.popularMovies;


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

    private List<Film> mFilm;


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

    /*
                 * If we already have cached results, just deliver them now. If we don't have any
                 * cached results, force a load.
                 */
    //Background Thread loaded
    @Override
    protected void onStartLoading() {
        if (mFilm != null) {
            deliverResult(mFilm);
        } else {
            forceLoad();
        }
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

    @Override
    public void deliverResult(List<Film> data) {
        mFilm = data;
        super.deliverResult(data);

    }
}
