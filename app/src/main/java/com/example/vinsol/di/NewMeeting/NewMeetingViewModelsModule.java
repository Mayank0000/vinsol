package com.example.vinsol.di.NewMeeting;

import androidx.lifecycle.ViewModel;

import com.example.vinsol.di.ViewModelKey;
import com.example.vinsol.ui.NewMeeting.NewMeetingViewModel;
import com.example.vinsol.ui.meetingList.MeetingViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class NewMeetingViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(NewMeetingViewModel.class)
    public abstract ViewModel bindNewMeetingViewModel(NewMeetingViewModel viewModel);

}
