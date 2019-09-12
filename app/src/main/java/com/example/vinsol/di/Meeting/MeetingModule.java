package com.example.vinsol.di.Meeting;



import android.app.Application;

import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.vinsol.ui.meetingList.MeetingListAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class MeetingModule {

    @MeetingScope
    @Provides
    static MeetingListAdapter provideSessionApi(){
        return new MeetingListAdapter();
    }

    @MeetingScope
    @Provides
    static DividerItemDecoration getDecoration(Application application){
        return new DividerItemDecoration(application, DividerItemDecoration.VERTICAL);
    }


}



















