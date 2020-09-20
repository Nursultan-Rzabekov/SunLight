package com.example.sunlightdesign.data.source.repositories

import com.example.sunlightdesign.data.source.AuthDataSource
import com.example.sunlightdesign.data.source.MessengerRepository
import com.example.sunlightdesign.data.source.remote.auth.entity.LoginResponse
import com.example.sunlightdesign.di.AppModule
import com.example.sunlightdesign.utils.Prefs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


class DefaultMessengerRepository @Inject constructor(
    @AppModule.TasksRemoteDataSource private val tasksRemoteDataSource: AuthDataSource,
    @AppModule.TasksLocalDataSource private val tasksLocalDataSource: AuthDataSource,
    private val prefs: Prefs,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : MessengerRepository {


}
