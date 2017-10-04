package com.example.gregorio.popularMovies;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Gregorio on 03/10/2017.
 */

public interface QueryReviewUtils {


    @GET("/movie/{filmID}/reviews?")
    Call<List<Review>> filmReviews(@Path("filmID") String filmID, @Query("api_key") String sort);


}
