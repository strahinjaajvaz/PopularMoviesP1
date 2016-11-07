package com.example.strahinja.popularmovies.Loaders;

import com.example.strahinja.popularmovies.Model.Movie;
import com.example.strahinja.popularmovies.Utils.PopularMoviesUtils;


import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class MovieListLoader extends AsyncTaskLoader<List<Movie>> {
    private static final String LOG_TAG = MovieListLoader.class.getName();
    private String mUrl;

    public MovieListLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
        if(mUrl == null) return null;
        List<Movie> list = PopularMoviesUtils.fetchTMDbMovieList(mUrl);
        return list;
    }
}
