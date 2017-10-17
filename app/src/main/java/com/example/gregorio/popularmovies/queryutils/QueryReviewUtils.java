package com.example.gregorio.popularmovies.queryutils;

import android.text.TextUtils;
import android.util.Log;

import com.example.gregorio.popularmovies.models.Review;

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


/**
 * Created by Gregorio on 03/10/2017.
 */

public class QueryReviewUtils {

    private static final String LOG_TAG = QueryReviewUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryMovieUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryMovieUtils (and an object instance of QueryMovieUtils is not needed).
     */
    private QueryReviewUtils() {
    }

    public static ArrayList<Review> fetchReviewData(String requestUrl) {
        Log.v(LOG_TAG, "TEST: Fetch Review Data");

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
        ArrayList<Review> reviews = extractResultsFromJson(jsonResponse);

        // Return the list of {@link Film}s
        return reviews;
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
     * Return a list of {@link Review} objects that has been built up from
     * parsing the given JSON response.
     */
    private static ArrayList<Review> extractResultsFromJson(String reviewsJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(reviewsJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding movies to
        ArrayList<Review> reviews = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(reviewsJSON);

            //Extract the JASONArray associated with the Key called results
            //which represents a list of results.
            JSONArray reviewsArray = baseJsonResponse.getJSONArray("results");

            // For each movie item in the Array, create an {@link Film} object
            for (int i = 0; i < reviewsArray.length(); i++) {

                // Get a single news at position (i) within the list of newses
                JSONObject currentReviews = reviewsArray.getJSONObject(i);

                // Extract the value for the key "author"
                String author;
                if (currentReviews.has("author")) {
                    author = currentReviews.getString("author");
                } else {
                    author = "N.A";
                }

                // Extract the value for the key film "content"
                String review;
                if (currentReviews.has("content")) {
                    review = currentReviews.getString("content");
                } else {
                    review = "N.A";
                }

                Log.v(LOG_TAG, "Author and Reviews are: " + author + " " + review);

                // Create a new {@link Film} object with the author, review
                // and url from the JSON response.
                Review reviewObject = new Review(author, review);

                // Add the new {@link Film} to the list of newses.
                reviews.add(reviewObject);

            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(LOG_TAG, "Problem parsing the MovieDB JSON results", e);
        }

        // Return the list of films
        return reviews;
    }





}
