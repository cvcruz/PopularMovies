package com.cecilevcruz.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by cecicruz on 7/1/16.
 */



public class MovieDetailActivity extends ActionBarActivity {

    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MovieDetailFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class MovieDetailFragment extends Fragment {
        private static final String LOG_TAG = MovieDetailFragment.class.getSimpleName();

        private String movieInfoStr;

        public MovieDetailFragment() {


        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
            Intent intent = getActivity().getIntent();

            if(intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                movieInfoStr = intent.getStringExtra(Intent.EXTRA_TEXT);
                Uri posterUri = Uri.parse(movieInfoStr);
                String title = intent.getStringExtra(Intent.EXTRA_TEXT + "_title");
                String overview = intent.getStringExtra(Intent.EXTRA_TEXT + "_overview");
                double avgVote = intent.getDoubleExtra(Intent.EXTRA_TEXT + "_avgVote",0);
                String releaseDate = intent.getStringExtra(Intent.EXTRA_TEXT + "_releaseDate");

                Picasso.with(getContext()).load(posterUri).into((ImageView) rootView.findViewById(R.id.detail_poster));
                ((TextView) rootView.findViewById(R.id.detail_title)).setText(title);
                ((TextView) rootView.findViewById(R.id.detail_overview)).setText(overview);
                ((TextView) rootView.findViewById(R.id.detail_avgVote)).setText("Average Vote: " + avgVote);
                ((TextView) rootView.findViewById(R.id.detail_releaseDate)).setText("Release Date: " + releaseDate);
            }
            return rootView;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.main, menu);
        }
    }
}