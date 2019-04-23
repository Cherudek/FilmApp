package com.example.gregorio.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gregorio.popularmovies.R;
import com.example.gregorio.popularmovies.data.FilmContract;
import com.squareup.picasso.Picasso;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.FavouriteHolder> {


    public static final String LOG_TAG = FavouritesAdapter.class.getSimpleName();

    private Context mContext;
    private Cursor mCursor;

    private FavoritesAdapterOnClickHandler mClickHandler;

    private ImageView filmPoster;
    private TextView originalTitle;

    public FavouritesAdapter(FavoritesAdapterOnClickHandler clickHandler, Context context) {
        mClickHandler = clickHandler;
        mContext = context;
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

        //mCursor.moveToFirst();

        mCursor.moveToPosition(position); // get to the right location in the cursor

        // Determine the values of the wanted data
        final int movieIdIndex = mCursor.getColumnIndex(FilmContract.favouriteFilmEntry.COLUMN_FILM_ID);
        final int movieTitleIndex = mCursor.getColumnIndex(FilmContract.favouriteFilmEntry.COLUMN_TITLE);
        final int movieDateIndex = mCursor.getColumnIndex(FilmContract.favouriteFilmEntry.COLUMN_RELEASE_DATE);
        final int movieImagePathIndex = mCursor.getColumnIndex(FilmContract.favouriteFilmEntry.COLUMN_POSTER_PATH);
        final int movieOverviewIndex = mCursor.getColumnIndex(FilmContract.favouriteFilmEntry.COLUMN_OVERVIEW);
        final int movieVoteIndex = mCursor.getColumnIndex(FilmContract.favouriteFilmEntry.COLUMN_VOTE_AVERAGE);

        final int filmId = mCursor.getInt(movieIdIndex);
        final String title = mCursor.getString(movieTitleIndex);
        final String date = mCursor.getString(movieDateIndex);
        final String imagePath = mCursor.getString(movieImagePathIndex);
        final String overview = mCursor.getString(movieOverviewIndex);
        final String vote = mCursor.getString(movieVoteIndex);

        holder.itemView.setTag(filmId);
        holder.itemView.setTag(title);
        holder.itemView.setTag(overview);
        holder.itemView.setTag(vote);
        holder.itemView.setTag(date);

        Picasso.get()
                .load("http://image.tmdb.org/t/p/w185/" + imagePath)
                .into(holder.img);

        Log.i(LOG_TAG, "Image Path is: " + imagePath);
    }

    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    /**
     * When data changes and a re-query occurs, this function swaps the old Cursor
     * with a newly updated Cursor (Cursor c) that is passed in.
     */
    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
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
            img = itemView.findViewById(R.id.favourite_imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();

            mCursor.moveToPosition(adapterPosition);

            final int movieIdIndex = mCursor.getColumnIndex(FilmContract.favouriteFilmEntry.COLUMN_FILM_ID);
            final int movieTitleIndex = mCursor.getColumnIndex(FilmContract.favouriteFilmEntry.COLUMN_TITLE);
            final int movieDateIndex = mCursor.getColumnIndex(FilmContract.favouriteFilmEntry.COLUMN_RELEASE_DATE);
            final int movieImagePathIndex = mCursor.getColumnIndex(FilmContract.favouriteFilmEntry.COLUMN_POSTER_PATH);
            final int movieOverviewIndex = mCursor.getColumnIndex(FilmContract.favouriteFilmEntry.COLUMN_OVERVIEW);
            final int movieVoteIndex = mCursor.getColumnIndex(FilmContract.favouriteFilmEntry.COLUMN_VOTE_AVERAGE);

            final String filmId = mCursor.getString(movieIdIndex);
            final String title = mCursor.getString(movieTitleIndex);
            final String overview = mCursor.getString(movieOverviewIndex);
            final String vote = mCursor.getString(movieVoteIndex);
            final String date = mCursor.getString(movieDateIndex);
            final String imagePath = mCursor.getString(movieImagePathIndex);

            mClickHandler.onClick(title, filmId, overview, date, imagePath, vote);

        }
    }


}
