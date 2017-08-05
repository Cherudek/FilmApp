package com.example.gregorio.filmpt1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.appcompat.*;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmHolder> {

    public static final String LOG_TAG = MainActivity.class.getName();

    private Context context;

    private int images[];

    /*
 * An on-click handler that we've defined to make it easy for an Activity to interface with
 * our RecyclerView
 */
    private FilmAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface FilmAdapterOnClickHandler {
        void onClick(int film);
    }

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


    public FilmAdapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public FilmHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_layout, null);

        FilmHolder filmHolder = new FilmHolder(layout);

        return filmHolder;
    }

    @Override
    public void onBindViewHolder(FilmHolder holder, int position) {
        holder.img.setImageResource(images[position]);

    }

    @Override
    public int getItemCount() {
        return images.length;
    }


    public class FilmHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView img;

        public FilmHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int adapterPosition = getAdapterPosition();
            Log.i(LOG_TAG, "TEST: film ID is " + adapterPosition);

            int film = images[adapterPosition];
            Log.i(LOG_TAG, "TEST: film ID is " + film);

            mClickHandler.onClick(film);

        }
    }


}
