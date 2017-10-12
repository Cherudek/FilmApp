package com.example.gregorio.popularMovies.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Gregorio on 06/10/2017.
 */

public class FavouriteFilmsProvider extends ContentProvider {

    public static final String LOG_TAG = FavouriteFilmsProvider.class.getSimpleName();


    /*
     * These constant will be used to match URIs with the data they are looking for. We will take
     * advantage of the UriMatcher class to make that matching MUCH easier than doing something
     * ourselves, such as using regular expressions.
    */

    public static final int CODE_FAVOURITE_FILMS = 100;
    public static final int CODE_FAVOURITE_FILM_ID = 101;

    /*
     * The URI Matcher used by this content provider. The leading "s" in this variable name
     * signifies that this UriMatcher is a static member variable of FavouriteFilmsProvider and is a
     * common convention in Android programming.
    */
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private FilmDbHelper mOpenHelper;

    /**
     * Creates the UriMatcher that will match each URI to the CODE_FAVOURITE_FILMS and
     * CODE_FAVOURITE_FILM_ID constants defined above.
     *
     * @return A UriMatcher that correctly matches the constants for CODE_FAVOURITE_FILMS and CODE_FAVOURITE_FILM_ID
     */
    public static UriMatcher buildUriMatcher() {

        /*
         * All paths added to the UriMatcher have a corresponding code to return when a match is
         * found. The code passed into the constructor of UriMatcher here represents the code to
         * return for the root URI. It's common to use NO_MATCH as the code for this case.
         */
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FilmContract.CONTENT_AUTHORITY;

        /*
         * For each type of URI you want to add, create a corresponding code. Preferably, these are
         * constant fields in your class so that you can use them throughout the class and you know
         * they aren't going to change. In PopularFilms, we use CODE_FAVOURITE_FILMS or CODE_FAVOURITE_FILM_ID.
         */

        /* This URI is content://com.example.android.popularMovies/favouriteFilms/ */
        matcher.addURI(authority, FilmContract.PATH_FAVOURITE_FILMS, CODE_FAVOURITE_FILMS);

        /*
         * This URI would look something like content://com.example.android.popularMovies/favouriteFilms/7
         * The "/#" signifies to the UriMatcher that if PATH_FAVOURITE_FILMS is followed by ANY number,
         * that it should return the CODE_FAVOURITE_FILM_ID code
         */
        matcher.addURI(authority, FilmContract.PATH_FAVOURITE_FILMS + "/#", CODE_FAVOURITE_FILM_ID);

        return matcher;
    }

    /**
     * In onCreate, we initialize our content provider on startup. This method is called for all
     * registered content providers on the application main thread at application launch time.
     * It must not perform lengthy operations, or application startup will be delayed.
     * <p>
     * Nontrivial initialization (such as opening, upgrading, and scanning
     * databases) should be deferred until the content provider is used (via {@link #query},
     * {@link #bulkInsert(Uri, ContentValues[])}, etc).
     * <p>
     * Deferred initialization keeps application startup fast, avoids unnecessary work if the
     * provider turns out not to be needed, and stops database errors (such as a full disk) from
     * halting application launch.
     *
     * @return true if the provider was successfully loaded, false otherwise
     */


    @Override
    public boolean onCreate() {
           /*
         * As noted in the comment above, onCreate is run on the main thread, so performing any
         * lengthy operations will cause lag in your app. Since FilmDbHelper's constructor is
         * very lightweight, we are safe to perform that initialization here.
         */
        mOpenHelper = new FilmDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor;

        /*
         * Here's the switch statement that, given a URI, will determine what kind of request is
         * being made and query the database accordingly.
         */
        switch (sUriMatcher.match(uri)) {

            /*
             * When sUriMatcher's match method is called with a URI that looks something like this
             *
             *      content://com.example.android.popularMovies/favouriteFilms/7
             *
             * sUriMatcher's match method will return the code that indicates to us that we need
             * to return the film for a id. The id in this code is an integer
             * and is at the very end of the URI (7) and can be accessed
             * programmatically using Uri's getLastPathSegment method.
             *
             * In this case, we want to return a cursor that contains one row of film data for
             * a particular id.
             */
            case CODE_FAVOURITE_FILM_ID: {

                /*
                 * In order to determine the id associated with this URI, we look at the last
                 * path segment. In the comment above, the last path segment is 7 and
                 * represents the integer associated with a specific film.
                 */
                String normalizedUtcDateString = uri.getLastPathSegment();

                /*
                 * The query method accepts a string array of arguments, as there may be more
                 * than one "?" in the selection statement. Even though in our case, we only have
                 * one "?", we have to create a string array that only contains one element
                 * because this method signature accepts a string array.
                 */
                String[] selectionArguments = new String[]{normalizedUtcDateString};

                cursor = mOpenHelper.getReadableDatabase().query(
                        /* Table we are going to query */
                        FilmContract.favouriteFilmEntry.TABLE_NAME,
                        /*
                         * A projection designates the columns we want returned in our Cursor.
                         * Passing null will return all columns of data within the Cursor.
                         * However, if you don't need all the data from the table, it's best
                         * practice to limit the columns returned in the Cursor with a projection.
                         */
                        projection,
                        /*
                         * The URI that matches CODE_FAVOURITE_FILM_ID contains an id at the end
                         * of it. We extract that id and use it with these next two lines to
                         * specify the row of film we want returned in the cursor. We use a
                         * question mark here and then designate selectionArguments as the next
                         * argument for performance reasons. Whatever Strings are contained
                         * within the selectionArguments array will be inserted into the
                         * selection statement by SQLite under the hood.
                         */
                        FilmContract.favouriteFilmEntry.COLUMN_FILM_ID + " = ? ",
                        selectionArguments,
                        null,
                        null,
                        sortOrder);

                break;
            }

             /*
             * When sUriMatcher's match method is called with a URI that looks EXACTLY like this
             *
             *      content://com.example.gregorio.popularMovies/favouriteFilms/
             *
             * sUriMatcher's match method will return the code that indicates to us that we need
             * to return all of the favourite films in our favouriteFilms table.
             *
             * In this case, we want to return a cursor that contains every row of favourite film data
             * in our favouriteFilms table.
             */

            case CODE_FAVOURITE_FILMS: {
                cursor = mOpenHelper.getReadableDatabase().query(
                        FilmContract.favouriteFilmEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        //Loader automatically reloads with latest data
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;

    }

    /**
     * In Popular Movies, we aren't going to do anything with this method. However, we are required to
     * override it as FavouriteFilmsProvider extends ContentProvider and getType is an abstract method in
     * ContentProvider. Normally, this method handles requests for the MIME type of the data at the
     * given URI. For example, if your app provided images at a particular URI, then you would
     * return an image URI from this method.
     *
     * @param uri the URI to query.
     * @return nothing in Sunshine, but normally a MIME type string, or null if there is no type.
     */

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        throw new RuntimeException("We are not implementing getType in Popular Films.");

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CODE_FAVOURITE_FILMS:
                return insertFilm(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a film into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertFilm(Uri uri, ContentValues values) {


        // Check that the id is not null
        Integer filmID = values.getAsInteger(FilmContract.favouriteFilmEntry.COLUMN_FILM_ID);
        if (filmID == null) {
            throw new IllegalArgumentException("Film requires an ID");
        }
        // Check that the Title is not null
        String title = values.getAsString(FilmContract.favouriteFilmEntry.COLUMN_TITLE);
        if (title == null) {
            throw new IllegalArgumentException("Film requires a Title");
        }

        // Check that the overview is not null
        String releaseDate = values.getAsString(FilmContract.favouriteFilmEntry.COLUMN_RELEASE_DATE);
        if (releaseDate == null) {
            throw new IllegalArgumentException("Film requires a release date");
        }


        // Check that the poster path is not null
        String posterPath = values.getAsString(FilmContract.favouriteFilmEntry.COLUMN_POSTER_PATH);
        if (posterPath == null) {
            throw new IllegalArgumentException("Film requires a Poster Path");
        }

        // Check that the overview is not null
        String overview = values.getAsString(FilmContract.favouriteFilmEntry.COLUMN_OVERVIEW);
        if (overview == null) {
            throw new IllegalArgumentException("Film requires an Overview");
        }


        // Check that the average vote is not null
        String voteAverage = values.getAsString(FilmContract.favouriteFilmEntry.COLUMN_VOTE_AVERAGE);
        if (voteAverage == null) {
            throw new IllegalArgumentException("Film requires an average vote");
        }


        // Get writeable database
        SQLiteDatabase database = mOpenHelper.getWritableDatabase();

        // Insert the new pet with the given values
        long rowID = database.insert(FilmContract.favouriteFilmEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (rowID == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        //Loader automatically reloads with latest data
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, rowID);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {


        // Get writeable database
        SQLiteDatabase database = mOpenHelper.getWritableDatabase();

        // Track the number of rows that were deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CODE_FAVOURITE_FILMS:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(FilmContract.favouriteFilmEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case CODE_FAVOURITE_FILM_ID:
                // Delete a single row given by the ID in the URI
                selection = FilmContract.favouriteFilmEntry.COLUMN_FILM_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(FilmContract.favouriteFilmEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        Log.i(LOG_TAG, "Rows deleted= " + rowsDeleted);

        // Return the number of rows deleted
        return rowsDeleted;

    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        throw new RuntimeException("We are not implementing update in Popular Films");

    }
}
