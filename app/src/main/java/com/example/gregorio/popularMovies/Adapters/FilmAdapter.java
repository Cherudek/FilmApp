package com.example.gregorio.popularMovies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.gregorio.popularMovies.Models.Film;
import com.example.gregorio.popularMovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmHolder> {

    public static final String LOG_TAG = FilmAdapter.class.getName();
    /*
 * An on-click handler that we've defined to make it easy for an Activity to interface with
 * our RecyclerView
 */
    private final FilmAdapterOnClickHandler mClickHandler;
    // A copy of the original mObjects array, initialized from and then used instead as soon as
    private List<Film> mMovieData = new ArrayList<>();

    /**
     * Creates a FilmAdapter.
     *
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     */
    public FilmAdapter(FilmAdapterOnClickHandler clickHandler, int numberOfItems) {
        mClickHandler = clickHandler;
        int items = numberOfItems;
    }

    @Override
    public FilmHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForGridItem = R.layout.grid_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForGridItem, viewGroup, shouldAttachToParentImmediately);
        return new FilmHolder(view);
    }

    @Override
    public void onBindViewHolder(FilmHolder holder, int position) {
        Film currentFilm = mMovieData.get(position);
        String filmPoster = currentFilm.getmThumbnail();
        Picasso.with(holder.img.getContext()).load("http://image.tmdb.org/t/p/w342/" + filmPoster).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return mMovieData.size();
    }

    /**
     * Adds the specified items at the end of the array.
     *
     * @param movies
     */
    public void addAll(List<Film> movies) {
        if (mMovieData != null)
            mMovieData.clear();
        mMovieData.addAll(movies);
        notifyDataSetChanged();
    }

    /**
     * Remove all elements from the list.
     */
    public void clear() {
        if (mMovieData != null)
            mMovieData.clear();
        notifyDataSetChanged();
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface FilmAdapterOnClickHandler {
        void onClick(String filmTitle, String id, String filmPlot, String release, String poster, String Rating);
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
            Film film = mMovieData.get(adapterPosition);

            String filmTitle = film.getmTitle();
            String filmId = film.getmId();
            String plot = film.getmPlot();
            String releaseDate = film.getmReleaseDate();
            String filmPoster = film.getmThumbnail();
            String filmRating = film.getmUserRating();

            mClickHandler.onClick(filmTitle, filmId, plot, releaseDate, filmPoster, filmRating);
        }
    }
}
