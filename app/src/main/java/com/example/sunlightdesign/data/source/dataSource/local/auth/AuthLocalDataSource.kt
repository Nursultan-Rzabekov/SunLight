package com.example.sunlightdesign.data.source.dataSource.local.auth

import com.example.sunlightdesign.data.source.dataSource.AuthDataSource
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.BaseResponse
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Login
import com.example.sunlightdesign.usecase.usercase.authUse.SetLogin
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


class AuthLocalDataSource internal constructor(
    private val tasksDao: AuthDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AuthDataSource {

    override suspend fun getTasks(model: SetLogin): Login {
        TODO()
    }

    override suspend fun setFirebaseToken(token: String): BaseResponse {
        TODO()
    }
}
