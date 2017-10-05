package com.example.gregorio.popularMovies.Models;

/**
 * Created by Gregorio on 05/10/2017.
 */

public class Trailer {

    //Fields

    private String mTrailerName;
    private String mTrailerId;

    public Trailer(String mTrailerName, String mTrailerId) {
        this.mTrailerName = mTrailerName;
        this.mTrailerId = mTrailerId;
    }

    public String getmTrailerName() {
        return mTrailerName;
    }

    public void setmTrailerName(String mTrailerName) {
        this.mTrailerName = mTrailerName;
    }

    public String getmTrailerId() {
        return mTrailerId;
    }

    public void setmTrailerId(String mTrailerId) {
        this.mTrailerId = mTrailerId;
    }
}
