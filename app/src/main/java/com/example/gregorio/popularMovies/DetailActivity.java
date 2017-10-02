package com.example.gregorio.popularMovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        Intent intentThatStartedThisActivity = getIntent();

        Film object = getIntent().getParcelableExtra(Intent.EXTRA_TEXT);

        Log.i(LOG_TAG, "Film object Parcelable " + object);

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {

                String mImage = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
                Picasso.with(mImageDisplay.getContext()).load("http://image.tmdb.org/t/p/w342/" + mImage).into(mImageDisplay);

                String title = intentThatStartedThisActivity.getStringExtra(MainActivity.EXTRA_TEXT_TITLE);
                mTitle.setText(title);

                String releaseDate = intentThatStartedThisActivity.getStringExtra(MainActivity.EXTRA_TEXT_RELEASE_DATE);
                mReleaseDate.setText(releaseDate);

                String userRating = intentThatStartedThisActivity.getStringExtra(MainActivity.EXTRA_TEXT_USER_RATING);
                mUserRating.setText(userRating);

                String plot = intentThatStartedThisActivity.getStringExtra(MainActivity.EXTRA_TEXT_PLOT);
                mPlot.setText(plot);
            }
        }
    }
}

