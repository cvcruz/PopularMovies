package com.cecilevcruz.popularmovies;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviePosterAdapter extends ArrayAdapter<MoviePoster> {
    private static final String LOG_TAG = MoviePosterAdapter.class.getSimpleName();

    /**
     * @param context        The current context. Used to inflate the layout file.
     * @param moviePosters A List of MoviePoster objects to display in a list
     */
    public MoviePosterAdapter(Activity context, List<MoviePoster> moviePosters) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, moviePosters);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The AdapterView position that is requesting a view
     * @param convertView The recycled view to populate.
     *                    (search online for "android view recycling" to learn more)
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Gets the moviePoster object from the ArrayAdapter at the appropriate position
        MoviePoster moviePoster = getItem(position);

        if (convertView == null) {
            convertView = new ImageView(getContext());
        }
        Picasso.with(getContext()).load(moviePoster.imgSrc).into((ImageView) convertView);
        return convertView;
    }
}