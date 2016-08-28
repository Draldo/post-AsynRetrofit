package com.example.draldo.retrofitapi;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.draldo.retrofitapi.Modules.Movies;
import com.example.draldo.retrofitapi.Modules.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://api.themoviedb.org/";
    private static final String TAG = MainActivity.class.getSimpleName() + "TAG_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<Result> call = retrofitInterface.getMovies();

        call.enqueue(new Callback<Result>() {
            int counter = 0;
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result result = response.body();
                for (Movies film : result.results){
                    counter += 1;
                    Log.d(TAG, "onCreate: " + String.valueOf(counter) + " " + film.title);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });

    }
}
