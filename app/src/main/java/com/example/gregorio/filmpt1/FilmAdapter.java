package com.example.gregorio.filmpt1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmHolder> {

    public static final String LOG_TAG = FilmAdapter.class.getName();
    /*
 * An on-click handler that we've defined to make it easy for an Activity to interface with
 * our RecyclerView
 */
    private final FilmAdapterOnClickHandler mClickHandler;
    // A copy of the original mObjects array, initialized from and then used instead as soon as
    private List<Film> mMovieData;
    private Context mContext;
    private LayoutInflater inflater;

    /**
     * Creates a FilmAdapter.
     *
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     *
     */
    public FilmAdapter(FilmAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @Override
    public FilmHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForGridItem = R.layout.grid_layout;
        inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForGridItem, parent, shouldAttachToParentImmediately);
        return new FilmHolder(view);
    }

    @Override
    public void onBindViewHolder(FilmHolder holder, int position) {

        //String film = mMovieData[position];
        Film currentFilm = mMovieData.get(position);
        Log.i(LOG_TAG, "TEST: Current Film is " + currentFilm);

        String filmPoster = currentFilm.getmThumbnail();
        Log.i(LOG_TAG, "TEST: Poster url is " + filmPoster);


        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/" + filmPoster).into(holder.img);
    }

    @Override
    public int getItemCount() {
        if (mMovieData == null) return 0;
        return mMovieData.toArray().length;
    }

    /**
     * Adds the specified items at the end of the array.
     *
     * @param movies
     */
    public void addAll(Film... movies) {
        if (mMovieData != null) {
            Collections.addAll(mMovieData, movies);
        }
        notifyDataSetChanged();
    }

    /**
     * Remove all elements from the list.
     */
    public void clear() {
        if (mMovieData != null) {
            mMovieData.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface FilmAdapterOnClickHandler {
        void onClick(String film);
    }

    // The Film Holder optimizes the calling and binding of views from the adaptor to the UI
    public class FilmHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView img;

        public FilmHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Log.i(LOG_TAG, "TEST: position ID is " + adapterPosition);
            String film = String.valueOf(mMovieData.get(adapterPosition));
            Log.i(LOG_TAG, "TEST: film ID is " + film);
            mClickHandler.onClick(String.valueOf(film));
        }
    }
}
