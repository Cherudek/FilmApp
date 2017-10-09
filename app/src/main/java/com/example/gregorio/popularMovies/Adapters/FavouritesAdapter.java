package com.example.gregorio.popularMovies.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gregorio.popularMovies.Data.FilmContract;
import com.example.gregorio.popularMovies.Models.Film;
import com.example.gregorio.popularMovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.FavouriteHolder> {

    public static final String LOG_TAG = FavouritesAdapter.class.getSimpleName();
    private static Context mContext;
    private FavoritesAdapterOnClickHandler mClickHandler;
    private List<Film> mMovieData = new ArrayList<>();
    private Cursor mCursor;

    private ImageView filmPoster;

    private TextView originalTitle;

    public FavouritesAdapter(FavoritesAdapterOnClickHandler clickHandler, int numberOfItems) {
        mClickHandler = clickHandler;
        int items = numberOfItems;
    }

    @Override
    public FavouriteHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        int layoutIdForGridItem = R.layout.favourites_grid_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForGridItem, viewGroup, shouldAttachToParentImmediately);
        return new FavouriteHolder(view);
    }

    @Override
    public void onBindViewHolder(FavouriteHolder holder, int position) {

        // Determine the values of the wanted data
        final int idIndex = mCursor.getInt(mCursor.getColumnIndexOrThrow(FilmContract.favouriteFilmEntry._ID));

        int movieIdIndex = mCursor.getColumnIndexOrThrow(FilmContract.favouriteFilmEntry.COLUMN_FILM_ID);
        int movieTitleIndex = mCursor.getColumnIndexOrThrow(FilmContract.favouriteFilmEntry.COLUMN_TITLE);
        int movieOverviewIndex = mCursor.getColumnIndexOrThrow(FilmContract.favouriteFilmEntry.COLUMN_OVERVIEW);
        int movieVoteIndex = mCursor.getColumnIndexOrThrow(FilmContract.favouriteFilmEntry.COLUMN_VOTE_AVERAGE);
        int movieDateIndex = mCursor.getColumnIndexOrThrow(FilmContract.favouriteFilmEntry.COLUMN_RELEASE_DATE);
        int movieImagePathIndex = mCursor.getColumnIndexOrThrow(FilmContract.favouriteFilmEntry.COLUMN_POSTER_PATH);

        mCursor.moveToPosition(position); // get to the right location in the cursor

        final int id = mCursor.getInt(movieIdIndex);
        String title = mCursor.getString(movieTitleIndex);
        String overview = mCursor.getString(movieOverviewIndex);
        String vote = mCursor.getString(movieVoteIndex);
        String date = mCursor.getString(movieDateIndex);
        String imagePath = mCursor.getString(movieImagePathIndex);

        holder.itemView.setTag(id);

//        holder.originalTitle.setText(title);
//        holder.movieOverview.setText(overview);
//        holder.voteAverage.setText(vote);
//        holder.releaseDate.setText(date);

        Picasso.with(holder.img.getContext())
                .load("http://image.tmdb.org/t/p/w185/" + imagePath)
                .into(holder.img);

    }

    @Override
    public int getItemCount() {
        return mMovieData.size();
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface FavoritesAdapterOnClickHandler {
        void onClick(String filmTitle, String id, String filmPlot, String release, String poster, String Rating);
    }

    // The Film Holder optimizes the calling and binding of views from the adaptor to the UI
    public class FavouriteHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView img;

        public FavouriteHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.favourite_imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Film film = mMovieData.get(adapterPosition);

            String filmTitle = film.getmTitle();
            String filmId = film.getmId();
            String plot = film.getmPlot();
            String releaseDate = film.getmReleaseDate();
            String filmPosterPath = film.getmThumbnail();
            String filmRating = film.getmUserRating();

            mClickHandler.onClick(filmTitle, filmId, plot, releaseDate, filmPosterPath, filmRating);

        }
    }


}
