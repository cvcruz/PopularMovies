package com.cecilevcruz.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by cecicruz on 7/1/16.
 */
public class MoviePosterAdapter extends BaseAdapter{
    private Context mpContext;

    public MoviePosterAdapter(Context context){
        mpContext = context;
    }

    public int getCount(){
        return moviePosters.length;
    }

    public Object getItem(int position){
        return null;
    }

    public long getItemId(int position){
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.v("MOVIEPOSTESRADAPTER:", String.valueOf(this.getMoviePosters().length));
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mpContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        //final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
        final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";

       // Uri builtUri = Uri.parse(IMAGE_BASE_URL).buildUpon()
        //        .appendPath()
        //        .build();
        Uri posterURI = Uri.parse(IMAGE_BASE_URL);
        imageView.setImageURI(posterURI);
        return imageView;
    }
    protected String[] moviePosters;

    public void setMoviePosters(String[] moviePostersArray){
        moviePosters = moviePostersArray;
    }
    public String[] getMoviePosters(){
        return moviePosters;
    }
}
