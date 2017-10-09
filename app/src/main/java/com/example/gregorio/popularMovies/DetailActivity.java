package com.example.gregorio.popularMovies;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gregorio.popularMovies.Adapters.ReviewAdapter;
import com.example.gregorio.popularMovies.Adapters.TrailerAdapter;
import com.example.gregorio.popularMovies.Data.FilmContract;
import com.example.gregorio.popularMovies.Loaders.ReviewLoader;
import com.example.gregorio.popularMovies.Loaders.TrailerLoader;
import com.example.gregorio.popularMovies.Models.Film;
import com.example.gregorio.popularMovies.Models.Review;
import com.example.gregorio.popularMovies.Models.Trailer;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailActivity extends AppCompatActivity implements TrailerAdapter.TrailerAdapterOnClickHandler {

    final static String API_KEY_PARAM = "api_key";
    final static String API_KEY = "21d79bfbb630e90306b78b394f98db52";

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    private static final int FILM_REVIEWS_LOADER_ID = 2;
    private static final int FILM_TRAILERS_LOADER_ID = 5;
    private static final String FILM_API_REQUEST_URL = "https://api.themoviedb.org/3/movie";
    private static final String FILM_REVIEWS = "reviews";
    private static final String FILM_TRAILERS = "trailers";
    // This state is when the film is not a favourite and clicking the button will therefore
    // insert the film to the favourites database
    private final int STATE_NOT_FAVOURITE = 0;
    // This state is when the film is a favourite and clicking the button will therefore
    // delete the film from our database
    private final int STATE_FAVOURITE = 1;
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
    @BindView(R.id.add_to_favourites)
    Button mAddToFavourites;
    @BindView(R.id.rv_review)
    RecyclerView rvListView;
    @BindView(R.id.rv_trailers)
    RecyclerView rvTrailers;
    @BindView(R.id.detail__error_message_display)
    TextView mErrorMessageDisplay;
    @BindView(R.id.detail_loading_spinner)
    ProgressBar mLoadingIndicator;
    @BindView(R.id.trailer_loading_spinner)
    ProgressBar mTrailerLoadingIndicator;
    /**
     * Content URI for the existing favourite Film (null if it's a new record)
     */
    private Uri mFavouriteFilmUri;
    // The current state of the app
    private int mCurrentState;
    /**
     * Content URI for the existing film (null if it's a new record)
     */
    private Uri mCurrentFilmUri;
    private String filmID;
    private String filmTitle;
    private String plot;
    private String releaseDate;
    private String poster;
    private String rating;
    private String trailerID;

    private LinearLayoutManager reviewsLayoutManager;
    private LinearLayoutManager trailerLayoutManager;

    private ReviewAdapter mReviewsAdapter;
    private TrailerAdapter mTrailersAdapter;



    private int numberOfReviews;
    private int numberOfTrailers;

    private Context mContext;
    LoaderManager.LoaderCallbacks<List<Trailer>> trailerLoader = new LoaderManager.LoaderCallbacks<List<Trailer>>() {

        @Override
        public Loader<List<Trailer>> onCreateLoader(int id, Bundle args) {
            //Uri builder to pass on the JSON query request
            Uri baseUri = Uri.parse(FILM_API_REQUEST_URL);
            Uri.Builder uriBuilder = baseUri.buildUpon();
            Uri.Builder baseAndfilmId = uriBuilder.appendEncodedPath(filmID);
            Uri.Builder filmIdTrailers = baseAndfilmId.appendEncodedPath(FILM_TRAILERS);
            Uri.Builder baseAndKey = filmIdTrailers.appendQueryParameter(API_KEY_PARAM, API_KEY);

            mContext = getApplicationContext();

            //returns a url string for the QueryMovieUtils background task
            Log.i(LOG_TAG, "URI is: " + baseAndKey);
            return new TrailerLoader(mContext, baseAndKey.toString());
        }

        @Override
        public void onLoadFinished(Loader<List<Trailer>> loader, List<Trailer> data) {
            mTrailerLoadingIndicator.setVisibility(View.GONE);

            // If there is a valid list of {@link movie}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                numberOfTrailers = data.size();
                mTrailersAdapter.addAll(data);
                showTrailersDataView();
            } else {
                showTrailerErrorMessage();
            }

        }

        @Override
        public void onLoaderReset(Loader<List<Trailer>> loader) {
            mTrailersAdapter.clear();
        }
    };


    private LoaderManager.LoaderCallbacks<List<Review>> reviewsLoader = new LoaderManager.LoaderCallbacks<List<Review>>() {

        @Override
        public Loader<List<Review>> onCreateLoader(int id, Bundle args) {

            //Uri builder to pass on the JSON query request
            Uri baseUri = Uri.parse(FILM_API_REQUEST_URL);
            Uri.Builder uriBuilder = baseUri.buildUpon();
            Uri.Builder baseAndfilmId = uriBuilder.appendEncodedPath(filmID);
            Uri.Builder filmIdReviews = baseAndfilmId.appendEncodedPath(FILM_REVIEWS);
            Uri.Builder baseAndKey = filmIdReviews.appendQueryParameter(API_KEY_PARAM, API_KEY);

            mContext = getApplicationContext();

            //returns a url string for the QueryMovieUtils background task
            Log.i(LOG_TAG, "URI is: " + baseAndKey);
            return new ReviewLoader(mContext, baseAndKey.toString());

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

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        Film object = getIntent().getParcelableExtra(Intent.EXTRA_TEXT);

        filmTitle = object.getmTitle();
        filmID = object.getmId();
        plot = object.getmPlot();
        releaseDate = object.getmReleaseDate();
        poster = object.getmThumbnail();
        rating = object.getmUserRating();

        mTitle.setText(filmTitle);
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

        trailerLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);

        // Use setLayoutManager on rvListView with the LinearLayoutManager we created above
        rvListView.setLayoutManager(reviewsLayoutManager);

        rvTrailers.setLayoutManager(trailerLayoutManager);

         /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        rvListView.setHasFixedSize(true);
        rvTrailers.setHasFixedSize(true);

        /*
         * The GreenAdapter is responsible for displaying each item in the list.
         */
        mReviewsAdapter = new ReviewAdapter(numberOfReviews);
        mTrailersAdapter = new TrailerAdapter(this, numberOfTrailers);

        //Set the ReviewAdapter you created on rvListView
        rvListView.setAdapter(mReviewsAdapter);
        rvTrailers.setAdapter(mTrailersAdapter);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();


        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            getSupportLoaderManager().initLoader(FILM_REVIEWS_LOADER_ID, null, reviewsLoader);
            getSupportLoaderManager().initLoader(FILM_TRAILERS_LOADER_ID, null, trailerLoader);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            mLoadingIndicator.setVisibility(View.GONE);
            // Update empty state with no connection error message
            mErrorMessageDisplay.setText(R.string.no_internet);
        }


        //Button to insert or delete a film to our favourite films database
        mAddToFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Either show the definition of the current word, or if the definition is currently
                // showing, move to the next word.
                switch (mCurrentState) {
                    case STATE_NOT_FAVOURITE:
                        insertToFavourite();
                        break;
                    case STATE_FAVOURITE:
                        showDeleteConfirmationDialog();
                        break;
                }
            }

        });

    }


    private void insertToFavourite() {

        ContentValues values = new ContentValues();
        values.put(FilmContract.favouriteFilmEntry.COLUMN_FILM_ID, filmID);
        values.put(FilmContract.favouriteFilmEntry.COLUMN_TITLE, filmTitle);
        values.put(FilmContract.favouriteFilmEntry.COLUMN_OVERVIEW, plot);
        values.put(FilmContract.favouriteFilmEntry.COLUMN_RELEASE_DATE, releaseDate);
        values.put(FilmContract.favouriteFilmEntry.COLUMN_VOTE_AVERAGE, rating);
        values.put(FilmContract.favouriteFilmEntry.COLUMN_POSTER_PATH, poster);

        // Determine if this is a new or existing record by checking if mCurrentRecordUri is null or not
        if (mCurrentFilmUri == null) {
            // This is a NEW record, so insert a new record into the provider,
            // returning the content URI for the new record.
            Uri newUri = getContentResolver().insert(FilmContract.favouriteFilmEntry.CONTENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful.
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.insert_film_failed),
                        Toast.LENGTH_SHORT).show();

            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.insert_film_successful),
                        Toast.LENGTH_SHORT).show();
            }

        } else {
            // Otherwise this is an EXISTING record, Send a Toast Message saying this film is already
            // in your Favourites
            Toast.makeText(this, getString(R.string.film_already_added),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Prompt the user to confirm that they want to delete this film from the database.
     */
    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the record.
                deleteFilm();
            }
        });
        builder.setNegativeButton(R.string.cancel, null);


        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Perform the deletion of the record in the database.
     */
    private void deleteFilm() {
        // Only perform the delete if this is an existing record.
        if (mCurrentFilmUri != null) {
            // Call the ContentResolver to delete the record at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentRecordUri
            // content URI already identifies the record that we want.
            int rowsDeleted = getContentResolver().delete(mCurrentFilmUri, null, null);

            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, getString(R.string.editor_delete_record_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_delete_record_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    protected void onResume() {
        getSupportLoaderManager().restartLoader(FILM_REVIEWS_LOADER_ID, null, reviewsLoader);
        getSupportLoaderManager().restartLoader(FILM_TRAILERS_LOADER_ID, null, trailerLoader);
        super.onResume();
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

    private void showTrailersDataView() {
        /* First, make sure the error is invisible */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        rvTrailers.setVisibility(View.VISIBLE);
    }

    private void showTrailerErrorMessage() {
        /* First, hide the currently visible data */
        rvTrailers.setVisibility(View.INVISIBLE);
        /* Then, show the error */
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(String trailerName, String trailerId) {

        Uri youtubeUrl = Uri.parse("https://www.youtube.com/watch?v=" + trailerId);
        Intent youTubeIntent = new Intent(Intent.ACTION_VIEW, youtubeUrl);
        youTubeIntent.addCategory(Intent.CATEGORY_BROWSABLE);
        startActivity(youTubeIntent);

    }





}

