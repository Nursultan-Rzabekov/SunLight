package com.example.sunlightdesign.ui.screens.tasks.di

import androidx.lifecycle.ViewModel
import com.example.sunlightdesign.di.ViewModelBuilderModule
import com.example.sunlightdesign.di.ViewModelKey
import com.example.sunlightdesign.ui.screens.tasks.TasksFragment
import com.example.sunlightdesign.ui.screens.tasks.TasksViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class TasksModule {

//    @ContributesAndroidInjector(modules = [
//        ViewModelBuilderModule::class
//    ])
//    internal abstract fun tasksFragment(): TasksFragment

    @Binds
    @IntoMap
    @ViewModelKey(TasksViewModel::class)
    abstract fun bindViewModel(viewmodel: TasksViewModel): ViewModel
}
