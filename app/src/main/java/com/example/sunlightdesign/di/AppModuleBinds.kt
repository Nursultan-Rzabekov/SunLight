package com.example.sunlightdesign.di

import com.example.sunlightdesign.data.source.DefaultTasksRepository
import com.example.sunlightdesign.data.source.TasksRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppModuleBinds {

    @Singleton
    @Binds
    abstract fun bindRepository(repo: DefaultTasksRepository): TasksRepository
}
