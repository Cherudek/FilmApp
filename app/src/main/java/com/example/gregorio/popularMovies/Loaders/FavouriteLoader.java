package com.example.gregorio.popularMovies.Loaders;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

import com.example.gregorio.popularMovies.Data.FavouriteFilmsProvider;


public class FavouriteLoader extends AsyncTaskLoader<Cursor> {

    private FavouriteFilmsProvider mFavouriteFilmUri;


    public FavouriteLoader(Context context) {
        super(context);
    }

    @Override
    public Cursor loadInBackground() {


        return null;
    }
}
