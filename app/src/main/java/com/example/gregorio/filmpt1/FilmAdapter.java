package com.example.gregorio.filmpt1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmHolder> {

    public static final String LOG_TAG = MainActivity.class.getName();

    //Dummy Images to test the the RecyclerView and the DetailView explicit Intent;
    /*
 * An on-click handler that we've defined to make it easy for an Activity to interface with
 * our RecyclerView
 */
    private final FilmAdapterOnClickHandler mClickHandler;

    private String[] mMovieData;


    // A copy of the original mObjects array, initialized from and then used instead as soon as
    private ArrayList<Film> mOriginalValues;

    /**
     * Contains the list of objects that represent the data of this ArrayAdapter.
     * The content of this list is referred to as "the array" in the documentation.
     */
    private List<Film> mObjects;


//    private int images[] = {
//            R.drawable.bluesbrothers, R.drawable.drive,
//            R.drawable.fight_club, R.drawable.grease, R.drawable.jaws,
//            R.drawable.pulp_fiction, R.drawable.star_wars, R.drawable.weekend,
//            R.drawable.ghostdog, R.drawable.leon, R.drawable.et, R.drawable.clockwork_orange
//    };

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
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForGridItem, parent, shouldAttachToParentImmediately);
        return new FilmHolder(view);
    }

    @Override
    public void onBindViewHolder(FilmHolder holder, int position) {
        String film = mMovieData[position];
        holder.img.setImageResource(Integer.parseInt(film));
    }

    @Override
    public int getItemCount() {
        if (mMovieData == null) return 0;
        return mMovieData.length;
    }

    /**
     * Adds the specified items at the end of the array.
     *
     * @param items The items to add at the end of the array.
     */
    public void addAll(Film... items) {

        if (mOriginalValues != null) {
            Collections.addAll(mOriginalValues, items);
        }

        notifyDataSetChanged();
    }

    /**
     * Remove all elements from the list.
     */
    public void clear() {

        if (mOriginalValues != null) {
            mOriginalValues.clear();
        }

        notifyDataSetChanged();
    }
//
//    public void addAll(ArrayList<Film> films, ArrayList<Film> items) {
//        items.addAll(films);
//        notifyDataSetChanged();
//    }
//
//
//
//    public void clear(ArrayList movies) {
//        movies.clear();
//        notifyDataSetChanged();
//    }

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
            String film = mMovieData[adapterPosition];
            Log.i(LOG_TAG, "TEST: film ID is " + film);
            mClickHandler.onClick(String.valueOf(film));
        }
    }
}
