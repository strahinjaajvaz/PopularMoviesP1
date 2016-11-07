package com.example.strahinja.popularmovies.Utils;

import com.example.strahinja.popularmovies.Model.Movie;

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
import java.util.List;


public class PopularMoviesUtils {
    private static final String IMAGE_RESOURCE_URL = "http://image.tmdb.org/t/p/w185/";

    private static final String LOG_TAG = PopularMoviesUtils.class.getName();

    private PopularMoviesUtils() {
    }

    public static List<Movie> fetchTMDbMovieList(String stringURL) {
        List<Movie> list = null;
        try {
            URL url = createUrl(stringURL);
            String jsonResult = returnJsonResult(url);
            list = extractMovieFromJson(jsonResult);
        } catch (Exception e) {
            return null;
        }

        return list;
    }

    private static URL createUrl(String stringURL) {
        URL url = null;
        try {
            url = new URL(stringURL);
        } catch (IOException e) {
            return null;
        }
        return url;
    }

    private static String returnJsonResult(URL url) {
        if (url == null) return null;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        String result = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(1000);
            urlConnection.setConnectTimeout(1500);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                result = readFromStream(inputStream);
            }
        } catch (IOException e) {

        } finally {
            if (urlConnection != null) urlConnection = null;
            if (inputStream != null) inputStream = null;
        }
        return result;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        if (inputStream == null) return null;

        StringBuilder sb = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);

        String line = reader.readLine();
        while (line != null) {
            sb.append(line);
            line = reader.readLine();
        }

        return sb.toString();
    }

    private static List<Movie> extractMovieFromJson(String jsonObject) {
        if (jsonObject == null) return null;

        List<Movie> list = null;
        try {

            JSONObject root = new JSONObject(jsonObject);
            JSONArray results = root.getJSONArray("results");

            list = new ArrayList<>();

            for (int i = 0; i < results.length(); i++) {
                JSONObject resultObject = results.getJSONObject(i);

                int id = resultObject.getInt("id");
                String title = resultObject.getString("title");
                String imageUrl = IMAGE_RESOURCE_URL + resultObject.getString("poster_path");
                String releaseDate = resultObject.getString("release_date");
                double voteAverage = resultObject.getDouble("vote_average");
                String plotSynopsis = resultObject.getString("overview");

                list.add(new Movie(
                        id,
                        title,
                        imageUrl,
                        releaseDate,
                        voteAverage,
                        plotSynopsis));
            }
        } catch (JSONException e) {

        }
        return list;
    }
}
