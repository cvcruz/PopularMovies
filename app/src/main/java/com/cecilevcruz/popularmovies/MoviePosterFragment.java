package com.cecilevcruz.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MoviePosterFragment extends Fragment{

    MoviePoster moviePosters;

    protected MoviePosterAdapter movieAdapter;


    public MoviePosterFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        movieAdapter = new MoviePosterAdapter(getActivity(), new ArrayList<MoviePoster>());
        View rootView = inflater.inflate(R.layout.fragment_movie_grid, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.gridview_movies);
        gridView.setAdapter(movieAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                String poster = (movieAdapter.getItem(i).imgSrc).toString();
                String title = movieAdapter.getItem(i).title;
                Intent movieDetailIntent = new Intent(getActivity(), MovieDetailActivity.class).putExtra(Intent.EXTRA_TEXT, poster)
                        .putExtra(Intent.EXTRA_TEXT + "_title", title)
                        .putExtra(Intent.EXTRA_TEXT + "_overview", movieAdapter.getItem(i).overview)
                        .putExtra(Intent.EXTRA_TEXT + "_releaseDate", movieAdapter.getItem(i).releaseDate);
                startActivity(movieDetailIntent);
            }
        });
        return rootView;
    }
    private void updateMovies(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String searchBy = prefs.getString(getString(R.string.pref_search_key),getString(R.string.pref_search_default));
        FetchMoviesTask fetchMoviesTask = new FetchMoviesTask();
        fetchMoviesTask.execute(searchBy);
    }

    @Override
    public void onStart(){
        super.onStart();
        updateMovies();
    }
    public class FetchMoviesTask extends AsyncTask<String, Void, JSONArray>{
        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

        private JSONArray getMovieDBDataFromJson(String moviesJsonStr, int numDays)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String API_RESULTS = "results";

            JSONObject movieJson = new JSONObject(moviesJsonStr);
            JSONArray moviesArray = movieJson.getJSONArray(API_RESULTS);
            return moviesArray;

        }
        @Override
        protected JSONArray doInBackground(String...params){
            if(params.length == 0){
                return null;
            }
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieDBJsonStr = null;

            int numMovies = 10;
            try {

                final String API_SORTBY = "sort_by";
                final String API_KEY = "api_key";
                final String BASE_URL = "http://api.themoviedb.org/3/movie";

                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                        .appendPath(params[0].toLowerCase())
                        .appendQueryParameter(API_KEY, BuildConfig.MOVIEDB_API_KEY)
                        .build();

                // Create the request to themoviedb, and open the connection
                URL url = new URL(builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    Log.v(LOG_TAG,"input stream null");
                    movieDBJsonStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    Log.v(LOG_TAG,"Stream was empty.  No point in parsing");
                    // Stream was empty.  No point in parsing.
                    movieDBJsonStr = null;
                }
                movieDBJsonStr = buffer.toString();

            } catch (IOException e) {
                Log.e(LOG_TAG, "IOException", e);
                // If the code didn't successfully get the movie data, there's no point in attempting
                // to parse it.
                movieDBJsonStr = null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "IOException: Error closing stream", e);
                    }
                }
            }

            try {
                //Log.v(LOG_TAG,"moviesDB json:" + movieDBJsonStr);
                return getMovieDBDataFromJson(movieDBJsonStr, numMovies);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONArray result){
            if (result != null){
               movieAdapter.clear();
                for(int i = 0; i < result.length(); i++) {
                    try {
                        movieAdapter.add(new MoviePoster(result.getJSONObject(i)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}