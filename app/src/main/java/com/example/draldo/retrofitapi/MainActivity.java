package com.example.draldo.retrofitapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.draldo.retrofitapi.modules.Movies;
import com.example.draldo.retrofitapi.modules.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://api.themoviedb.org/";
    private static final String TAG = MainActivity.class.getSimpleName() + "TAG_";

    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxAdapter)
                .build();

        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Observable<Result> call = retrofitInterface.getMovies();
        subscription = call
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Result>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            HttpException response = (HttpException)e;
                            int code = response.code();
                        }
                    }

                    @Override
                    public void onNext(Result result) {
                        for (Movies film: result.results){
                            Log.d(TAG, "onNext: " + film.title);
                        }
                    }
                });

//        call.enqueue(new Callback<Result>() {
//            int counter = 0;
//            @Override
//            public void onResponse(Call<Result> call, Response<Result> response) {
//                Result result = response.body();
//                for (Movies film : result.results){
//                    counter += 1;
//                    Log.d(TAG, "onCreate: " + String.valueOf(counter) + " " + film.title);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Result> call, Throwable t) {
//
//            }
//        });

    }

    @Override
    protected void onDestroy() {
        this.subscription.unsubscribe();
        super.onDestroy();
    }
}
