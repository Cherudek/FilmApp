package com.example.gregorio.popularMovies.Loaders;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by Gregorio on 09/10/2017.
 */

public class FavouriteLoader extends AsyncTaskLoader<Cursor> {


    public FavouriteLoader(Context context) {
        super(context);
    }

    @Override
    public Cursor loadInBackground() {
        return null;
    }
}
