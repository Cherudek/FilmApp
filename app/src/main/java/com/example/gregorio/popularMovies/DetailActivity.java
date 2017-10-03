package com.example.gregorio.popularMovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailActivity extends AppCompatActivity {

    final static String API_KEY_PARAM = "api_key";
    private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    private static final int FILM_REVIEWS_LOADER_ID = 2;
    private static final String FILM_API_REQUEST_URL = "https://api.themoviedb.org/3/movie/";
    private static final String FILM_REVIEWS = "reviews?";
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
    private String filmID;
    private RecyclerView reviewsRecyclerView;
    private GridLayoutManager reviewsLayoutManager;
    private FilmAdapter mReviewsAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
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


    }
}

