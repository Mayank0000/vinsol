package com.example.vinsol.di.Meeting;

import androidx.lifecycle.ViewModel;

import com.example.vinsol.di.ViewModelKey;
import com.example.vinsol.ui.NewMeeting.NewMeetingViewModel;
import com.example.vinsol.ui.meetingList.MeetingViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MeetingViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(MeetingViewModel.class)
    public abstract ViewModel bindAuthViewModel(MeetingViewModel viewModel);


}
