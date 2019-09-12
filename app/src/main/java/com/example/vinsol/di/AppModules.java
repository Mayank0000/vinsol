package com.example.vinsol.di;

import com.example.vinsol.util.Constants;
import com.example.vinsol.Repository.Repository;
import com.example.vinsol.network.Api;

import java.util.Calendar;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModules {

    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance(){
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    static Calendar getInstance(){
        return Calendar.getInstance();
    }

    @Provides
    @Singleton
    Api getApiCall(Retrofit retrofit) {
        return retrofit.create(Api.class);
    }

    @Provides
    @Singleton
    Repository getRepository(Api apiCall) {
        return new Repository(apiCall);
    }

}
