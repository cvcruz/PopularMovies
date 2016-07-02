package com.cecilevcruz.popularmovies;

/**
 * Created by cecicruz on 7/1/16.
 */

/**
 * Created by poornima-udacity on 6/26/15.
 */
public class MoviePoster {
    int image; // drawable reference id
    /*
    Movie details layout contains title, release date, movie poster, vote average, and plot synopsis.
    */

    String imgSrc;


    public MoviePoster(int image)
    {
        this.image = image;
    }

    public MoviePoster(String imageSrc)
    {

        this.imgSrc = imageSrc;
    }

}