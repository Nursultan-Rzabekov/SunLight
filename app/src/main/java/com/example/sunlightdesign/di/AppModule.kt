package com.example.sunlightdesign.di

import android.content.Context
import androidx.room.Room
import com.example.sunlightdesign.data.source.DefaultTasksRepository
import com.example.sunlightdesign.data.source.TasksDataSource
import com.example.sunlightdesign.data.source.TasksRepository
import com.example.sunlightdesign.data.source.local.TasksLocalDataSource
import com.example.sunlightdesign.data.source.local.ToDoDatabase
import com.example.sunlightdesign.data.source.remote.ApiServices
import com.example.sunlightdesign.data.source.remote.TasksRemoteDataSource
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.annotation.AnnotationRetention.RUNTIME

@Module
object AppModule {

    @Qualifier
    @Retention(RUNTIME)
    annotation class TasksRemoteDataSource

    @Qualifier
    @Retention(RUNTIME)
    annotation class TasksLocalDataSource

    @JvmStatic
    @Singleton
    @TasksRemoteDataSource
    @Provides
    fun provideTasksRemoteDataSource(apiServices: ApiServices): TasksDataSource {
        return TasksRemoteDataSource(apiServices)
    }

    @JvmStatic
    @Singleton
    @TasksLocalDataSource
    @Provides
    fun provideTasksLocalDataSource(
        database: ToDoDatabase,
        ioDispatcher: CoroutineDispatcher
    ): TasksDataSource {
        return TasksLocalDataSource(
            database.taskDao(), ioDispatcher
        )
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideDataBase(context: Context): ToDoDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ToDoDatabase::class.java,
            "Tasks.db"
        ).build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}
