package com.example.sunlightdesign.ui.screens.home.di

import androidx.lifecycle.ViewModel
import com.example.sunlightdesign.di.ViewModelKey
import com.example.sunlightdesign.ui.screens.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class HomeModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindViewModel(viewmodel: HomeViewModel): ViewModel
}
