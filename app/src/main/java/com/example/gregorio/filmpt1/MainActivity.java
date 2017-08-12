package com.example.gregorio.filmpt1;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import static com.example.gregorio.filmpt1.QueryUtils.fetchMovieData;

public class MainActivity extends AppCompatActivity implements FilmAdapter.FilmAdapterOnClickHandler, LoaderManager.LoaderCallbacks<List<Film>> {

    final static String API_KEY_PARM = "api_key=";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int FILM_LOADER_ID = 1;
    private static final String DEFAULT_MOVIE_DB_URL = "http://image.tmdb.org/t/p/movie/popular";

    private static final String apiKey = "21d79bfbb630e90306b78b394f98db52";

    private static final String MOVIE_DB_API_REQUEST_URL = "http://api.themoviedb.org/3/movie/popular?api_key=21d79bfbb630e90306b78b394f98db52";

    private RecyclerView recyclerView;

    private GridLayoutManager layoutManager;

    private FilmAdapter mFilmAdapter;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /* This TextView is used to display errors and will be hidden if there are no errors */
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        layoutManager = new GridLayoutManager(MainActivity.this, 2);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);

        mFilmAdapter = new FilmAdapter(this);

        recyclerView.setAdapter(mFilmAdapter);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.loading_spinner);

        loadMovieData();

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(FILM_LOADER_ID, null, this);
            Log.v(LOG_TAG, "TEST: Calling the LoaderCallBack");

        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_spinner);
            loadingIndicator.setVisibility(View.GONE);
            // Update empty state with no connection error message
            mErrorMessageDisplay.setText(R.string.no_internet);
            Log.i(LOG_TAG, "TEST: No Internet Connectivity");

        }
    }

    @Override
    public Loader<List<Film>> onCreateLoader(int i, Bundle bundle) {
        Log.v(LOG_TAG, "TEST: New Loader initialised for the url provided");
        //onCreateLoader() method to read the user’s latest preferences for the minimum magnitude,
        //construct a proper URI with their preference, and then create a new Loader for that URI.

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        Uri baseUri = Uri.parse(MOVIE_DB_API_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("api-key", "591c3b4c-30e9-4ac7-8ded-8d3f1086c46e");
        //uriBuilder.appendQueryParameter("orderby", orderBy);

        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Film>> loader, List<Film> movies) {
        Log.v(LOG_TAG, "TEST: Loader Cleared");

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_spinner);
        loadingIndicator.setVisibility(View.GONE);

        // Clear the adapter of previous movie data
        mFilmAdapter.clear();

        // If there is a valid list of {@link movie}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (movies != null && !movies.isEmpty()) {
            mFilmAdapter.addAll(newses);
        } else {
            showErrorMessage();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Film>> loader) {
        Log.v(LOG_TAG, "TEST: Loader cleared of existing data");

        // Loader reset, so we can clear out our existing data.
        mFilmAdapter.clear();
    }




    /**
     * This method will get the user's preferred location for weather, and then tell some
     * background method to get the weather data in the background.
     */
    private void loadMovieData() {
        showMovieDataView();
        //new FetchMoviesTask().execute();
        fetchMovieData(MOVIE_DB_API_REQUEST_URL);
    }

    /**
     * This method will make the View for the weather data visible and
     * hide the error message.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showMovieDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        /* First, hide the currently visible data */
        recyclerView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(String film) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, film);
        startActivity(intentToStartDetailActivity);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

//    public class FetchMoviesTask extends AsyncTask<String, Void, String[]> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            mLoadingIndicator.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected String[] doInBackground(String... params) {
//
//            /* If there's no zip code, there's nothing to look up. */
//            if (params.length == 0) {
//                return null;
//            }
//
//            String sortBy = params[0];
//            URL movieRequestUrl = QueryUtils.buildUrl(sortBy);
//
//            try {
//                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
//
//                String[] simpleJsonWeatherData = fetchMovieData(jsonMovieResponse);
//
//
//                return simpleJsonWeatherData;
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String[] movieData) {
//            mLoadingIndicator.setVisibility(View.INVISIBLE);
//            if (movieData != null) {
//                showMovieDataView();
//                recyclerView.setVisibility(View.VISIBLE);
//            } else {
//                showErrorMessage();
//            }
//        }
//
//    }
//


