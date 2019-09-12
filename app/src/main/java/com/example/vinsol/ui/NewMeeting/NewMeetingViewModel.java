package com.example.vinsol.ui.NewMeeting;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.vinsol.Repository.Repository;
import com.example.vinsol.model.Date;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class NewMeetingViewModel extends ViewModel {

    private Repository repository;
    private MediatorLiveData<List<Date>> cachedUser = new MediatorLiveData<>();

    @Inject
    public NewMeetingViewModel(Repository repository) {
        this.repository = repository;
    }


    public void authenticateWithId(String message){

        final LiveData<List<Date>> source= queryUserId(message);

        cachedUser.setValue((List<Date>)null);
        cachedUser.addSource(source, new Observer<List<Date>>() {
            @Override
            public void onChanged(List<Date> userSendMessageResource) {
                cachedUser.setValue(userSendMessageResource);
                cachedUser.removeSource(source);
            }
        });
    }

    public LiveData<List<Date>> getAuthUser(){
        return cachedUser;
    }


    private LiveData<List<Date>> queryUserId(final String date){

        return LiveDataReactiveStreams.fromPublisher(
                repository.executeUserListApi(date)

                        // instead of calling onError, do this
                        .onErrorReturn(new Function<Throwable, List<Date>>() {
                            @Override
                            public List<Date> apply(Throwable throwable) throws Exception {
                                List<Date> dateList = new ArrayList<>();
                                Date errorUser = new Date();
                                errorUser.setDescription("Error");
                                dateList.add(errorUser);
                                return dateList;
                            }
                        })
                        // wrap User object in SendMessageResource
                        .map(new Function<List<Date>, List<Date>>() {
                            @Override
                            public List<Date> apply(List<Date> user) throws Exception {
                                return user;
                            }
                        })
                        .subscribeOn(Schedulers.io()));
    }

}






