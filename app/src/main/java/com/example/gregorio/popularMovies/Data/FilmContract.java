package com.example.gregorio.popularMovies.Data;


import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines table and column names for the favourite films database. This class is not necessary, but keeps
 * the code organized.
 */

public class FilmContract {

    /*
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website. A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * Play Store.
     */

    public static final String CONTENT_AUTHORITY = "com.example.gregorio.popularMovies";

    /*
 * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
 * the content provider for Popular Movies.
 */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    /*
     * Possible paths that can be appended to BASE_CONTENT_URI to form valid URI's that Popular Movies
     * can handle. For instance,
     *
     *     content://com.example.android.popularMovies/favouriteFilms/
     *     [           BASE_CONTENT_URI         ][ PATH_FAVOURITE_FILMS ]
     *
     * is a valid path for looking at favourite films database.
     *
     *      content://com.example.android.popularMovies/givemeroot/
     *
     * will fail, as the ContentProvider hasn't been given any information on what to do with
     * "givemeroot". At least, let's hope not. Don't be that dev, reader. Don't be that dev.
     */
    public static final String PATH_FAVOURITE_FILMS = "favouriteFilms";

    /* Inner class that defines the table contents of the favourite films table */
    public static final class favouriteFilmEntry implements BaseColumns {

        /* The base CONTENT_URI used to query the favourite films table from the content provider */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FAVOURITE_FILMS)
                .build();

        /* Used internally as the name of our favourite films table. */
        public static final String TABLE_NAME = "favouriteFilms";

        //Column ID <P>Type: INTEGER</P>
        public static final String _ID = BaseColumns._ID;

        /* Film id as returned by API, used to identify the id to be used */
        public static final String COLUMN_FILM_ID = "id";

        /* Film title as returned by API, used to identify the title to be used */
        public static final String COLUMN_TITLE = "title";

        /* Film release date as returned by API */
        public static final String COLUMN_RELEASE_DATE = "release_date";

        /* Film poster path date as returned by APIto get the film poster */
        public static final String COLUMN_POSTER_PATH = "poster_path";

        /* Film overview to get the synopsis of the film */
        public static final String COLUMN_OVERVIEW = "overview";

        /* Film vote average to get the average votes for a film */
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";

    }
}
