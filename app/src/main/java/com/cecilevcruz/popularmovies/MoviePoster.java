package com.cecilevcruz.popularmovies;

/**
 * Created by cecicruz on 7/1/16.
 */

import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MoviePoster extends ArrayList<MoviePoster> {
    private String LOG_TAG = MoviePoster.class.getSimpleName();
    /*
    Movie details layout contains title, release date, movie poster, vote average, and plot synopsis.
    */

    Uri imgSrc;
    String title;
    String releaseDate;
    String overview;
    double avgVote;

    public MoviePoster(JSONObject movie) {
        final String API_KEY_TITLE = "title";
        final String API_KEY_SYNOPSIS = "overview";
        final String API_KEY_AVGVOTE= "vote_average";
        final String API_KEY_RELEASEDATE= "release_date";

        try {
            this.imgSrc = getMoviePosterUri(movie.getString("poster_path"));
            this.title = movie.getString(API_KEY_TITLE);
            this.overview = movie.getString(API_KEY_SYNOPSIS);
            this.avgVote = movie.getDouble(API_KEY_AVGVOTE);
            this.releaseDate = movie.getString(API_KEY_RELEASEDATE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Uri getMoviePosterUri(String imageSrc){
        // Construct the URL for themoviedb.org query
        // available sizes: "w92", "w154", "w185", "w342", "w500", "w780", or "original"
        final String API_SIZE = "w500";
        final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";

        Uri imageUri = Uri.parse(IMAGE_BASE_URL).buildUpon()
                .appendPath(API_SIZE)
                .appendEncodedPath(imageSrc)
                .build();
        return imageUri;
    }
}