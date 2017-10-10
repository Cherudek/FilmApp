package com.example.gregorio.popularMovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gregorio.popularMovies.Adapters.FavouritesAdapter;

/**
 * Created by Gregorio on 08/10/2017.
 */

public class FavouriteActivity extends AppCompatActivity implements FavouritesAdapter.FavoritesAdapterOnClickHandler {

    private static final String LOG_TAG = FavouriteActivity.class.getSimpleName();

    private static final int FILM_FAVOURITE_LOADER_ID = 77;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    private int numberOfFavourites;

    private RecyclerView recyclerViewFavourites;

    private GridLayoutManager layoutManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.acitivity_favourite);

    }

    @Override
    public void onClick(String filmTitle, String id, String filmPlot, String release, String poster, String Rating) {

    }
}
