package com.example.gregorio.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gregorio.popularmovies.DetailActivity;
import com.example.gregorio.popularmovies.R;
import com.example.gregorio.popularmovies.models.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private static final String LOG_TAG = TrailerAdapter.class.getSimpleName();

    /*
    * An on-click handler that we've defined to make it easy for an Activity to interface with
    * our RecyclerView
    */
    private final TrailerAdapter.TrailerAdapterOnClickHandler mClickHandler;

    private int mNumberItems;

    // A copy of the original mObjects array, initialized from and then used instead as soon as
    private List<Trailer> mTrailerData = new ArrayList<>();

    /**
     * Constructor for TrailerAdapter that accepts a number of items to display and the specification
     * for the ListItemClickListener.
     *
     * @param numberOfItems Number of items to display in list
     */
    public TrailerAdapter(TrailerAdapter.TrailerAdapterOnClickHandler clickHandler, int numberOfItems) {
        mNumberItems = numberOfItems;
        mClickHandler = clickHandler;
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout. See
     *                  {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new NumberViewHolder that holds the View for each list item
     */
    @Override
    public TrailerAdapter.TrailerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.list_item_trailer;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        TrailerAdapter.TrailerViewHolder viewHolder = new TrailerAdapter.TrailerViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {

        Trailer currentTrailer = mTrailerData.get(position);

        String trailerName = currentTrailer.getmTrailerName();
        String trailerId = currentTrailer.getmTrailerId();

        holder.listItemTrailerName.setText(trailerName);
        Picasso.with(holder.listItemTrailerImage.getContext()).load("https://img.youtube.com/vi/" + trailerId + "/0.jpg").into(holder.listItemTrailerImage);

        Class destinationClass = DetailActivity.class;

        //Parcel data to film object to send data to DetailActivity
        Trailer dataToSend = new Trailer(trailerName, trailerId);

        dataToSend.setmTrailerId(trailerId);

        Log.i(LOG_TAG, "");
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our forecast
     */
    @Override
    public int getItemCount() {
        return mTrailerData.size();
    }

    /**
     * Adds the specified items at the end of the array.
     *
     * @param trailers
     */
    public void addAll(List<Trailer> trailers) {
        if (mTrailerData != null)
            mTrailerData.clear();
        mTrailerData.addAll(trailers);
        notifyDataSetChanged();
    }

    /**
     * Remove all elements from the list.
     */
    public void clear() {
        if (mTrailerData != null)
            mTrailerData.clear();
        notifyDataSetChanged();
    }

    public interface TrailerAdapterOnClickHandler {
        void onClick(String trailerName, String trailerId);
    }

    /**
     * A class called ReviewViewHolder that extends RecyclerView.ViewHolder
     * Cache of the children views for a list item.
     */
    class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        // Within ReviewViewHolder, create 2 x TextViews variable called listItemAuthor
        // Will display the name of the reviewer in the list, and one called listItemReview to display the actual review
        final TextView listItemTrailerName;
        final ImageView listItemTrailerImage;

        //  Create a constructor for NumberViewHolder that accepts a View called itemView as a parameter

        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews and set an onClickListener to listen for clicks. Those will be handled in the
         * onClick method below.
         *
         * @param itemView The View that you inflated in
         *                 {@link ReviewAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public TrailerViewHolder(View itemView) {
            // Within the constructor, call super(itemView) and then find listItemNumberView by ID
            super(itemView);

            listItemTrailerName = (TextView) itemView.findViewById(R.id.trailer_name);
            listItemTrailerImage = (ImageView) itemView.findViewById(R.id.trailer_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Trailer trailer = mTrailerData.get(adapterPosition);

            String trailerName = trailer.getmTrailerName();
            String trailerId = trailer.getmTrailerId();

            mClickHandler.onClick(trailerName, trailerId);
        }
    }


}
