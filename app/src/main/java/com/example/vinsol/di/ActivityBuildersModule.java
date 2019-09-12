package com.example.vinsol.di;


import com.example.vinsol.di.Meeting.MeetingModule;
import com.example.vinsol.di.Meeting.MeetingScope;
import com.example.vinsol.di.Meeting.MeetingViewModelsModule;
import com.example.vinsol.di.NewMeeting.NewMeetingModule;
import com.example.vinsol.di.NewMeeting.NewMeetingScope;
import com.example.vinsol.di.NewMeeting.NewMeetingViewModelsModule;
import com.example.vinsol.ui.NewMeeting.NewMeetingActivity;
import com.example.vinsol.ui.NewMeeting.NewMeetingViewModel;
import com.example.vinsol.ui.meetingList.MeetingListActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @MeetingScope
    @ContributesAndroidInjector(
            modules = {MeetingViewModelsModule.class, MeetingModule.class}
    )
    abstract MeetingListActivity contributeAuthActivity();


    @NewMeetingScope
    @ContributesAndroidInjector(modules  ={NewMeetingViewModelsModule.class, NewMeetingModule.class}
    )
    abstract NewMeetingActivity contributeNewMeetingActivity();

}
