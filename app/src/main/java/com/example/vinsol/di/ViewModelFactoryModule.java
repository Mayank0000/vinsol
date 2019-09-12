package com.example.vinsol.di;

import androidx.lifecycle.ViewModelProvider;

import com.example.vinsol.viewmodels.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelFactory);
}
