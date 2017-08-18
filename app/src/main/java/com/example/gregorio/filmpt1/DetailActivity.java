package com.example.gregorio.filmpt1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class DetailActivity extends AppCompatActivity {

    private ImageView mImageDisplay;
    private TextView mTitle;
    private TextView mReleaseDate;
    private TextView mUserRating;
    private TextView mPlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mImageDisplay = (ImageView) findViewById(R.id.posterDisplay);
        mTitle = (TextView) findViewById(R.id.title);
        mPlot = (TextView) findViewById(R.id.plot);
        mReleaseDate = (TextView) findViewById(R.id.release_date);
        mUserRating = (TextView) findViewById(R.id.user_rating);

        Intent intentThatStartedThisActivity = getIntent();

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

