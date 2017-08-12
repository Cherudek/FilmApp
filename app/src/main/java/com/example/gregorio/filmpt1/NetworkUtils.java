package com.example.gregorio.filmpt1;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 *************** CLASS NOT IN USE ****************
 * These utilities will be used to communicate with the database servers.
 */


public final class NetworkUtils {

    //TODO Check Query Utils Against Network Utils


    final static String popularMovies = "/movie/popular";
    final static String topRatedMovies = "/movie/top_rated";
    final static String API_KEY_PARM = "api_key=";


    /*
     * NOTE: These values only effect responses from OpenWeatherMap, NOT from the fake weather
     * server. They are simply here to allow us to teach you how to build a URL if you were to use
     * a real API.If you want to connect your app to OpenWeatherMap's API, feel free to! However,
     * we are not going to show you how to do so in this course.
     */
    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String MOVIE_DB_URL = "http://image.tmdb.org/t/p";
    private static final String DEFAULT_MOVIE_DB_URL = "http://image.tmdb.org/t/p/movie/popular";
    /* The API KEY to Access the Database */
    private static final String apiKey = "21d79bfbb630e90306b78b394f98db52";

    /**
     * Builds the URL used to talk to the weather server using a location. This location is based
     * on the query capabilities of the weather provider that we are using.
     *
     * @param sortBy The location that will be queried for.
     * @return The URL to use to query the weather server.
     */
    public static URL buildUrl(String sortBy) {
        Uri builtUri = Uri.parse(DEFAULT_MOVIE_DB_URL).buildUpon()
                .appendPath(sortBy)
                .appendQueryParameter(API_KEY_PARM, apiKey)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }


    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
