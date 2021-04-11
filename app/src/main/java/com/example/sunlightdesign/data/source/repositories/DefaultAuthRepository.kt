package com.example.sunlightdesign.data.source.repositories

import com.example.sunlightdesign.data.source.AuthRepository
import com.example.sunlightdesign.data.source.dataSource.AuthDataSource
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.BaseResponse
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Login
import com.example.sunlightdesign.usecase.usercase.authUse.SetFirebaseTokenUseCase
import com.example.sunlightdesign.usecase.usercase.authUse.SetLogin
import com.example.sunlightdesign.utils.SecureSharedPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import timber.log.Timber


class DefaultAuthRepository constructor(
    private val tasksRemoteDataSource: AuthDataSource,
    private val tasksLocalDataSource: AuthDataSource,
    private val prefs: SecureSharedPreferences,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AuthRepository {

    private val setFirebaseTokenUseCase = SetFirebaseTokenUseCase(this)

    override suspend fun getTasks(model: SetLogin): Login {
        val tasks = tasksRemoteDataSource.getTasks(model)
        prefs.bearerToken = tasks.token
        prefs.userId = tasks.user?.id.toString()
        prefs.firebaseToken?.let {
            if (it.isBlank()) return@let
            setFirebaseTokenUseCase.setModel(it)
            setFirebaseTokenUseCase.execute {
                onComplete {
                    Timber.d("firebase token is sent")
                }
                onError {
                    Timber.d("firebase token is not sent: $it")
                }
                onNetworkError {
                    Timber.d("firebase token is not sent: $it")
                }
            }
        }
        return tasks
    }

    override suspend fun setFirebaseToken(token: String): BaseResponse {
        return tasksRemoteDataSource.setFirebaseToken(token)
    }
}
