package com.cecilevcruz.popularmovies;

/**
 * Created by cecicruz on 7/1/16.
 */

import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by poornima-udacity on 6/26/15.
 */
public class MoviePoster extends ArrayList<MoviePoster> {

    /*
    Movie details layout contains title, release date, movie poster, vote average, and plot synopsis.
    */

    Uri imgSrc;
    String title;
    String releaseDate;
    String overview;
    double avgVote;

    public MoviePoster(JSONObject movie) {
        try {
            this.imgSrc = getMoviePosterUri(movie.getString("poster_path"));
            this.title = movie.getString("title");
            this.overview = movie.getString("overview");
            this.avgVote = movie.getDouble("vote_average");
            this.releaseDate = movie.getString("release_date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Uri getMoviePosterUri(String imageSrc){
        // Construct the URL for themoviedb.org query
        // available sizes: "w92", "w154", "w185", "w342", "w500", "w780", or "original"
        final String API_SIZE = "w185"; // recommended
        final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";

        Uri imageUri = Uri.parse(IMAGE_BASE_URL).buildUpon()
                .appendPath(API_SIZE)
                .appendEncodedPath(imageSrc)
                .build();
        return imageUri;
    }

}