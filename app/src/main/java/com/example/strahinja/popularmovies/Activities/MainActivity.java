package com.example.strahinja.popularmovies.Activities;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.strahinja.popularmovies.Adapters.ImageAdapter;
import com.example.strahinja.popularmovies.Loaders.MovieListLoader;
import com.example.strahinja.popularmovies.Model.Movie;
import com.example.strahinja.popularmovies.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<List<Movie>> {

    private static final String LOG_TAG = MainActivity.class.getName();

    private static final int MOVIE_LOADER_ID = 1;

    private ImageAdapter mImageAdapter;
    private GridView mGridView;
    private TextView mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGridView = (GridView) findViewById(R.id.gird_view);
        mImageAdapter = new ImageAdapter(this, new ArrayList<Movie>());
        mGridView.setAdapter(mImageAdapter);

        mEmptyView = (TextView) findViewById(R.id.empty_view);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(MOVIE_LOADER_ID, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.loading_spinner);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyView.setText(R.string.error_no_internet_connection);
        }

        ((GridView) findViewById(R.id.gird_view)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie currentMovie = (Movie) parent.getItemAtPosition(position);
                Intent movieDetailsIntent = new Intent(MainActivity.this, MovieDetails.class);
                movieDetailsIntent.putExtra("selectedMovie", currentMovie);
                if (movieDetailsIntent.resolveActivity(getPackageManager()) != null)
                    startActivity(movieDetailsIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.activity_main_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sortBy = sharedPreferences.getString(
                getString(R.string.settings_orderby_key),
                getString(R.string.settings_popular_movies_value));

        Uri.Builder builder = new Uri.Builder()
                .scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(sortBy)
                .appendQueryParameter("api_key", "255dffcc8ba799d5a86b9691a4f8775a");

        return new MovieListLoader(this, builder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        findViewById(R.id.loading_spinner).setVisibility(View.GONE);
        mImageAdapter.clear();
        if (movies != null && !movies.isEmpty()) mImageAdapter.addAll(movies);
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        mImageAdapter.clear();
    }
}
