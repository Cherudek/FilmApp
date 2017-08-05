package com.example.gregorio.filmpt1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class DetailActivity extends AppCompatActivity {

    private ImageView mImageDisplay;

    private String mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mImageDisplay = (ImageView) findViewById(R.id.posterDisplay);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                mImage = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);

            mImageDisplay.setImageResource(Integer.parseInt(mImage));
            }
        }
    }

}
