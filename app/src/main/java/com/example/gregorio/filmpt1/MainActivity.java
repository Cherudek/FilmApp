package com.example.gregorio.filmpt1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements FilmAdapter.FilmAdapterOnClickHandler {

    RecyclerView recyclerView;

    GridLayoutManager layoutManager;

    private FilmAdapter mFilmAdapter;


    private int filmPosters [] = {
            R.drawable.bluesbrothers, R.drawable.drive,
            R.drawable.fight_club, R.drawable.grease, R.drawable.jaws,
            R.drawable.pulp_fiction, R.drawable.star_wars
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        layoutManager = new GridLayoutManager(MainActivity.this, 2);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);

        mFilmAdapter = new FilmAdapter(MainActivity.this, filmPosters);

        recyclerView.setAdapter(mFilmAdapter);


    }

    @Override
    public void onClick(int film) {
        Context context = this;
        Class detailClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, detailClass);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, film);
        startActivity(intentToStartDetailActivity);


    }
}
