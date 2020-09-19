package com.example.sunlightdesign.di

import android.content.Context
import androidx.room.Room
import com.example.sunlightdesign.data.source.AuthDataSource
import com.example.sunlightdesign.data.source.local.AuthLocalDataSource
import com.example.sunlightdesign.data.source.local.ToDoDatabase
import com.example.sunlightdesign.data.source.remote.ApiServices
import com.example.sunlightdesign.data.source.remote.AuthRemoteDataSource
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
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

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiServices = retrofit.create(ApiServices::class.java)


    @JvmStatic
    @Singleton
    @TasksRemoteDataSource
    @Provides
    fun provideTasksRemoteDataSource(apiServices: ApiServices): AuthDataSource {
        return AuthRemoteDataSource(apiServices)
    }

    @JvmStatic
    @Singleton
    @TasksLocalDataSource
    @Provides
    fun provideTasksLocalDataSource(
        database: ToDoDatabase,
        ioDispatcher: CoroutineDispatcher
    ): AuthDataSource {
        return AuthLocalDataSource(
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
            "Auth.db"
        ).build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}
