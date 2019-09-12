package com.example.vinsol.Repository;

import com.example.vinsol.model.Date;
import com.example.vinsol.network.Api;

import java.util.List;

import javax.inject.Inject;
import io.reactivex.Flowable;
import retrofit2.Call;


public class Repository {

    private Api apiCall;

    @Inject
    public Repository(Api apiCallInterface) {
        this.apiCall = apiCallInterface;
    }

    public Flowable<List<Date>> executeUserListApi(String date) {
        return apiCall.getUser(date);
    }

}
