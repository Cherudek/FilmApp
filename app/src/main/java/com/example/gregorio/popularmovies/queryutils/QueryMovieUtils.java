package com.example.gregorio.popularmovies.queryutils;

import android.text.TextUtils;
import android.util.Log;

import com.example.gregorio.popularmovies.models.Film;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static com.example.gregorio.popularmovies.adapters.FilmAdapter.LOG_TAG;


public class QueryMovieUtils {

    /**
     * Create a private constructor because no one should ever create a {@link QueryMovieUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryMovieUtils (and an object instance of QueryMovieUtils is not needed).
     */
    private QueryMovieUtils() {
    }

    public static ArrayList<Film> fetchMovieData(String requestUrl) {
        Log.v(LOG_TAG, "TEST: Fetch Film Data");

        // Create URL object
        URL url = createUrl(requestUrl);

//        //2 seconds delay added before fetching data to show progress bar
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Film}s
        ArrayList<Film> movies = extractResultsFromJson(jsonResponse);

        // Return the list of {@link Film}s
        return movies;
    }


    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }


    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.v(LOG_TAG, "Built URI " + url);

                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the MovieDB JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Film} objects that has been built up from
     * parsing the given JSON response.
     */
    private static ArrayList<Film> extractResultsFromJson(String moviesJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(moviesJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding movies to
        ArrayList<Film> movies = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(moviesJSON);

            //Extract the JASONArray associated with the Key called results
            //which represents a list of results.
            JSONArray filmArray = baseJsonResponse.getJSONArray("results");

            // For each movie item in the Array, create an {@link Film} object
            for (int i = 0; i < filmArray.length(); i++) {

                // Get a single news at position (i) within the list of newses
                JSONObject currentMovies = filmArray.getJSONObject(i);

                // Extract the value for the key "title"
                String title;
                if (currentMovies.has("title")) {
                    title = currentMovies.getString("title");
                } else {
                    title = "N.A";
                }

                // Extract the value for the key film "id"
                String id;
                if (currentMovies.has("id")) {
                    id = currentMovies.getString("id");
                } else {
                    id = "N.A";
                }

                // Extract the value for the key "release_date"
                String releaseDate;
                if (currentMovies.has("release_date")) {
                    releaseDate = currentMovies.getString("release_date");
                } else {
                    releaseDate = "N.A.";
                }

                // Extract the value for the key "poster_path"
                String posterPath;
                if (currentMovies.has("poster_path")) {
                    posterPath = currentMovies.getString("poster_path");
                } else {
                    posterPath = "N.A.";
                }

                // Extract the value for the key "overview"
                String overview;
                if (currentMovies.has("overview")) {
                    overview = currentMovies.getString("overview");
                } else {
                    overview = "N.A.";
                }

                // Extract the value for the key "rating"
                String rating;
                if (currentMovies.has("vote_average")) {
                    rating = currentMovies.getString("vote_average");
                } else {
                    rating = "N.A.";
                }

                Log.i(LOG_TAG, "film title =" + title);

                // Create a new {@link Film} object with the title, section, time,
                // and url from the JSON response.
                Film movie = new Film(title, id, overview, releaseDate, posterPath, rating);

                // Add the new {@link Film} to the list of newses.
                movies.add(movie);

            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryMovieUtils", "Problem parsing the MovieDB JSON results", e);
        }

        // Return the list of films
        return movies;
    }

}
