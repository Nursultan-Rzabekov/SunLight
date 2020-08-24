package com.example.sunlightdesign.ui.screens.tasks.di

import androidx.lifecycle.ViewModel
import com.example.sunlightdesign.di.ViewModelKey
import com.example.sunlightdesign.ui.screens.tasks.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class HomeModule {

//    @ContributesAndroidInjector(modules = [
//        ViewModelBuilderModule::class
//    ])
//    internal abstract fun tasksFragment(): TasksFragment

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindViewModel(viewmodel: HomeViewModel): ViewModel
}
