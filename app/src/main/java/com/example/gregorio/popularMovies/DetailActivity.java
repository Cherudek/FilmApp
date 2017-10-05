package com.example.gregorio.popularMovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gregorio.popularMovies.Adapters.ReviewAdapter;
import com.example.gregorio.popularMovies.Loaders.ReviewLoader;
import com.example.gregorio.popularMovies.Models.Film;
import com.example.gregorio.popularMovies.Models.Review;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Review>> {

    final static String API_KEY_PARAM = "api_key";
    final static String API_KEY = "21d79bfbb630e90306b78b394f98db52";

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    private static final int FILM_REVIEWS_LOADER_ID = 2;
    private static final String FILM_API_REQUEST_URL = "https://api.themoviedb.org/3/movie";
    private static final String FILM_REVIEWS = "reviews";

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.posterDisplay)
    ImageView mImageDisplay;
    @BindView(R.id.plot)
    TextView mPlot;
    @BindView(R.id.user_rating)
    TextView mUserRating;
    @BindView(R.id.release_date)
    TextView mReleaseDate;
    @BindView(R.id.rv_review)
    RecyclerView rvListView;
    @BindView(R.id.detail__error_message_display)
    TextView mErrorMessageDisplay;
    @BindView(R.id.detail_loading_spinner)
    ProgressBar mLoadingIndicator;

    private String filmID;

    private LinearLayoutManager reviewsLayoutManager;
    private ReviewAdapter mReviewsAdapter;

    private int numberOfReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        Film object = getIntent().getParcelableExtra(Intent.EXTRA_TEXT);

        String filmTitle = object.getmTitle();
        filmID = object.getmId();
        String plot = object.getmPlot();
        String releaseDate = object.getmReleaseDate();
        String poster = object.getmThumbnail();
        String rating = object.getmUserRating();

        mTitle.setText(filmTitle + " " + "ID: " + filmID);
        mPlot.setText(plot);
        mReleaseDate.setText(releaseDate);
        Picasso.with(mImageDisplay.getContext()).load("http://image.tmdb.org/t/p/w342/" + poster).into(mImageDisplay);
        mUserRating.setText(rating);

        /*
         * A LinearLayoutManager is responsible for measuring and positioning item views within a
         * RecyclerView into a linear list. This means that it can produce either a horizontal or
         * vertical list depending on which parameter you pass in to the LinearLayoutManager
         * constructor. By default, if you don't specify an orientation, you get a vertical list.
         * In our case, we want a vertical list, so we don't need to pass in an orientation flag to
         * the LinearLayoutManager constructor.
         *
         * There are other LayoutManagers available to display your data in uniform grids,
         * staggered grids, and more! See the developer documentation for more details.
         */

        reviewsLayoutManager = new LinearLayoutManager(this);

        // Use setLayoutManager on rvListView with the LinearLayoutManager we created above
        rvListView.setLayoutManager(reviewsLayoutManager);

         /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        rvListView.setHasFixedSize(true);

        /*
         * The GreenAdapter is responsible for displaying each item in the list.
         */
        mReviewsAdapter = new ReviewAdapter(numberOfReviews);

        //Set the ReviewAdapter you created on rvListView
        rvListView.setAdapter(mReviewsAdapter);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            android.app.LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            getSupportLoaderManager().initLoader(FILM_REVIEWS_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            mLoadingIndicator.setVisibility(View.GONE);
            // Update empty state with no connection error message
            mErrorMessageDisplay.setText(R.string.no_internet);
        }

    }


    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(FILM_REVIEWS_LOADER_ID, null, this);
        super.onResume();
    }

    @Override
    public Loader<List<Review>> onCreateLoader(int id, Bundle args) {

        //Uri builder to pass on the JSON query request
        Uri baseUri = Uri.parse(FILM_API_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        Uri.Builder baseAndfilmId = uriBuilder.appendEncodedPath(filmID);
        Uri.Builder filmIdReviews = baseAndfilmId.appendEncodedPath(FILM_REVIEWS);
        Uri.Builder baseAndKey = filmIdReviews.appendQueryParameter(API_KEY_PARAM, API_KEY);

        //returns a url string for the QueryMovieUtils background task
        Log.i(LOG_TAG, "URI is: " + baseAndKey);
        return new ReviewLoader(this, baseAndKey.toString());

    }

    @Override
    public void onLoadFinished(Loader<List<Review>> loader, List<Review> data) {

        mLoadingIndicator.setVisibility(View.GONE);

        // If there is a valid list of {@link movie}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            numberOfReviews = data.size();
            mReviewsAdapter.addAll(data);
            showMovieDataView();
        } else {
            showErrorMessage();
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Review>> loader) {
        // Loader reset, so we can clear out our existing data.
        mReviewsAdapter.clear();

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
        rvListView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        /* First, hide the currently visible data */
        rvListView.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }


}

