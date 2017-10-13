package com.example.gregorio.popularMovies;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gregorio.popularMovies.Adapters.FavouritesAdapter;
import com.example.gregorio.popularMovies.Data.FilmContract;
import com.example.gregorio.popularMovies.Models.Film;

/**
 * Created by Gregorio on 08/10/2017.
 */

public class FavouriteActivity extends AppCompatActivity implements FavouritesAdapter.FavoritesAdapterOnClickHandler, LoaderManager.LoaderCallbacks<Cursor> {

    /*
    * The columns of data that we are interested in requesting from our db
    */
    public static final String[] projection = {

            FilmContract.favouriteFilmEntry.COLUMN_FILM_ID,
            FilmContract.favouriteFilmEntry.COLUMN_TITLE,
            FilmContract.favouriteFilmEntry.COLUMN_RELEASE_DATE,
            FilmContract.favouriteFilmEntry.COLUMN_POSTER_PATH,
            FilmContract.favouriteFilmEntry.COLUMN_OVERVIEW,
            FilmContract.favouriteFilmEntry.COLUMN_VOTE_AVERAGE,
    };
    /*
    * We store the indices of the values in the array of Strings above to more quickly be able to
    * access the data from our query. If the order of the Strings above changes, these indices
    * must be adjusted to match the order of the Strings.
    */
    public static final int INDEX_FILM_ID = 0;
    public static final int INDEX_TITLE = 1;
    public static final int INDEX_RELEASE_DATE = 2;
    public static final int INDEX_POSTER_PATH = 3;
    public static final int INDEX_OVERVIEW = 4;
    public static final int INDEX_VOTE_AVERAGE = 5;
    private static final String LOG_TAG = FavouriteActivity.class.getSimpleName();
    private static final int FILM_FAVOURITE_LOADER_ID = 77;

    private FavouritesAdapter mFavouritesAdapter;

    private RecyclerView recyclerViewFavourites;

    private int mPosition = RecyclerView.NO_POSITION;

    private ProgressBar mLoadingIndicator;

    private TextView mErrorMessageDisplay;

    private GridLayoutManager layoutManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        getSupportActionBar().setElevation(0f);

        recyclerViewFavourites = (RecyclerView) findViewById(R.id.favourites_recycler_view);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.favourites_loading_spinner);

        /* This TextView is used to display errors and will be hidden if there are no errors */
        mErrorMessageDisplay = (TextView) findViewById(R.id.favourites_error_message_display);

        layoutManager = new GridLayoutManager(this, numberOfColumns());

        mFavouritesAdapter = new FavouritesAdapter(this, this);

        recyclerViewFavourites.setHasFixedSize(true);
        recyclerViewFavourites.setLayoutManager(layoutManager);
        recyclerViewFavourites.setAdapter(mFavouritesAdapter);


        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();
        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(FILM_FAVOURITE_LOADER_ID, null, this);

    }

    // this method dynamically calculate the number of columns and
    // the layout would adapt to the screen size and orientation

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the poster
        int widthDivider = 300;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }


    //restart the loader to check whether we have a new sort-by value coming from the settings menu
    @Override
    protected void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(FILM_FAVOURITE_LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        switch (id) {

            case FILM_FAVOURITE_LOADER_ID:
                 /* URI for all rows of weather data in our weather table */
                Uri forecastQueryUri = FilmContract.favouriteFilmEntry.CONTENT_URI;

                // This loader will execute the ContentProvider's query method on a background thread
                return new CursorLoader(this,   // Parent activity context
                        forecastQueryUri,   // Provider content URI to query
                        projection,             // Columns to include in the resulting Cursor
                        null,                   // No selection clause
                        null,                   // No selection arguments
                        null);                  // Default sort order

            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        boolean cursorHasValidData = false;
        if (data != null && data.moveToFirst()) {
            /* We have valid data, continue on to bind the data to the UI */
            cursorHasValidData = true;

            mFavouritesAdapter.swapCursor(data);
            if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
            recyclerViewFavourites.smoothScrollToPosition(mPosition);


            if (data.getCount() != 0)
                // Hide loading indicator because the data has been loaded
                mLoadingIndicator.setVisibility(View.GONE);
            recyclerViewFavourites.setVisibility(View.VISIBLE);
        } else if (!cursorHasValidData) {
            /* No data to display, simply return and do nothing */
            mErrorMessageDisplay.setText(getString(R.string.error_message));
            return;
        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        /*
         * Since this Loader's data is now invalid, we need to clear the Adapter that is
         * displaying the data.
         */
        mFavouritesAdapter.swapCursor(null);
    }


    @Override
    public void onClick(String filmTitle, String id, String filmPlot, String release, String poster, String rating) {

        Context context = this;
        Class destinationClass = DetailActivity.class;

        //Parcel data to film object to send data to DetailActivity
        Film dataToSend = new Film();

        dataToSend.setmTitle(filmTitle);
        dataToSend.setmId(id);
        dataToSend.setmPlot(filmPlot);
        dataToSend.setmReleaseDate(release);
        dataToSend.setmThumbnail(poster);
        dataToSend.setmUserRating(rating);

        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, dataToSend);

        startActivity(intentToStartDetailActivity);

//        //Second Method to launch Detail Activity
//
//        Intent weatherDetailIntent = new Intent(FavouriteActivity.this, DetailActivity.class);
//        Uri uriForDateClicked = FilmContract.buildFavouriteUriwithID(Integer.parseInt(id));
//        weatherDetailIntent.setData(uriForDateClicked);
//        startActivity(weatherDetailIntent);

    }
}
