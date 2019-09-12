package com.example.vinsol.network;

import com.example.vinsol.model.Date;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @GET("api/schedule")
    Flowable<List<Date>> getUser(@Query("date") String date);


}
