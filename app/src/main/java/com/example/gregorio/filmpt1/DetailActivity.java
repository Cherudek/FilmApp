package com.example.gregorio.filmpt1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class DetailActivity extends AppCompatActivity {

    private ImageView mImageDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mImageDisplay = (ImageView) findViewById(R.id.posterDisplay);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                String mImage = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
                //mImageDisplay.setImageResource(Integer.parseInt(mImage));
                Picasso.with(mImageDisplay.getContext()).load("http://image.tmdb.org/t/p/w342/" + mImage).into(mImageDisplay);

            }
        }
            }
        }

