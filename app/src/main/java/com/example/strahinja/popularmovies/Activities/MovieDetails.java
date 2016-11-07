package com.example.strahinja.popularmovies.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.strahinja.popularmovies.Model.Movie;
import com.example.strahinja.popularmovies.R;
import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Movie movie = (Movie) getIntent().getExtras().getParcelable("selectedMovie");

        TextView title = (TextView) findViewById(R.id.activity_movie_details_title);
        title.setText(movie.getTitle());
        TextView releaseDate = (TextView) findViewById(R.id.activity_movie_details_release);
        releaseDate.setText(movie.getReleaseDate());
        TextView rating = (TextView) findViewById(R.id.activity_movie_details_rating);
        rating.setText(movie.getVoteAverage() + "/10");
        TextView synopsis = (TextView) findViewById(R.id.activity_movie_details_synopsis);
        synopsis.setText(movie.getPlotSynopsis());

        ImageView image = (ImageView) findViewById(R.id.details_image);
        Picasso.with(this).load(movie.getImageUrl()).error(R.drawable.noimagefound).into(image);
    }

}
