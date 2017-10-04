package com.example.gregorio.popularMovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DetailActivity extends AppCompatActivity {

    final static String API_KEY_PARAM = "api_key=21d79bfbb630e90306b78b394f98db52";
    final static String API_KEY = "21d79bfbb630e90306b78b394f98db52";
    private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    private static final int FILM_REVIEWS_LOADER_ID = 2;
    private static final String FILM_API_REQUEST_URL = "https://api.themoviedb.org/3/";
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
    @BindView(R.id.list_view)
    ListView listView;



    private String filmID;

    private GridLayoutManager reviewsLayoutManager;
    private ReviewAdapter mReviewsAdapter;
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


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(FILM_API_REQUEST_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        QueryReviewUtils client = retrofit.create(QueryReviewUtils.class);
        Call<List<Review>> call = client.filmReviews(filmID, API_KEY_PARAM);

        Log.i(LOG_TAG, "Api Reviews url is:" + call);


        call.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                List<Review> reviews = response.body();

                listView.setAdapter(new ReviewAdapter(DetailActivity.this, reviews));
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "error :(", Toast.LENGTH_SHORT).show();
            }
        });


    }
}

