package com.example.draldo.retrofitapi;

import com.example.draldo.retrofitapi.modules.Result;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Draldo on 27/08/2016.
 */
public interface RetrofitInterface {

    @GET("/3/discover/movie?sort_by=popularity.desc&api_key=4204f00567ba65b8b4e418ab5a02f3b3")
    Observable<Result> getMovies();

}
