package com.example.sunlightdesign.data.source.repositories

import com.example.sunlightdesign.data.source.AuthDataSource
import com.example.sunlightdesign.data.source.AuthRepository
import com.example.sunlightdesign.data.source.remote.auth.entity.LoginResponse

import com.example.sunlightdesign.usecase.usercase.authUse.SetLogin
import com.example.sunlightdesign.utils.Prefs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers



class DefaultAuthRepository  constructor(
     private val tasksRemoteDataSource: AuthDataSource,
     private val tasksLocalDataSource: AuthDataSource,
    private val prefs: Prefs,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AuthRepository {

    override suspend fun getTasks(model: SetLogin): LoginResponse {
        return tasksRemoteDataSource.getTasks(model)
    }
}
