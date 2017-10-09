package com.example.gregorio.popularMovies;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Gregorio on 08/10/2017.
 */

public class FavouriteActivity extends AppCompatActivity {

    private static final String LOG_TAG = FavouriteActivity.class.getSimpleName();

    private static final int FILM_FAVOURITE_LOADER_ID = 77;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    private int numberOfFavourites;

    private RecyclerView recyclerViewFavourites;

    private GridLayoutManager layoutManager;


}
