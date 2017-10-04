package com.example.gregorio.popularMovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Gregorio on 03/10/2017.
 */

public class ReviewAdapter extends ArrayAdapter<Review> {

    private Context context;
    private List<Review> values;

    public ReviewAdapter(Context context, List<Review> values) {
        super(context, R.layout.activity_detail, values);

        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item_review, parent, false);
        }

        TextView tvAuthor = (TextView) row.findViewById(R.id.author);
        TextView tvReview = (TextView) row.findViewById(R.id.review);

        Review item = values.get(position);
        String message = item.getmAuthor();
        tvAuthor.setText(message);
        String review = item.getmReview();
        tvReview.setText(review);

        return row;
    }
}
