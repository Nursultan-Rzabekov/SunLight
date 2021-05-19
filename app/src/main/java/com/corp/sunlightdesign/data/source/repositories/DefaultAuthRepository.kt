package com.corp.sunlightdesign.data.source.repositories

import com.corp.sunlightdesign.data.source.AuthRepository
import com.corp.sunlightdesign.data.source.dataSource.AuthDataSource
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.BaseResponse
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.Login
import com.corp.sunlightdesign.usecase.usercase.authUse.SetFirebaseTokenUseCase
import com.corp.sunlightdesign.usecase.usercase.authUse.SetLogin
import com.corp.sunlightdesign.utils.SecureSharedPreferences
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
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

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Timber.d("Fetching FCM registration token failed ${task.exception}")
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            token ?: return@OnCompleteListener

            // Log and toast
            val msg = "user token $token"
            Timber.d(msg)

            setFirebaseTokenUseCase.setModel(token)
            setFirebaseTokenUseCase.execute {
                onComplete {
                    Timber.d("firebase token $token is sent")
                }
                onError {
                    Timber.d("firebase token $token is not sent: $it")
                }
                onNetworkError {
                    Timber.d("firebase token $token is not sent: $it")
                }
            }
        })

        return tasks
    }

    override suspend fun setFirebaseToken(token: String): BaseResponse {
        return tasksRemoteDataSource.setFirebaseToken(token)
    }
}
