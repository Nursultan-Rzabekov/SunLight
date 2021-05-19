package com.corp.sunlightdesign.utils

import com.corp.sunlightdesign.data.source.dataSource.remote.auth.AuthServices
import com.corp.sunlightdesign.usecase.usercase.authUse.SetFirebaseTokenUseCase
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import okhttp3.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class HeaderInterceptor : Interceptor, KoinComponent {
    val sharedPreferences: SecureSharedPreferences by inject()
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer ${sharedPreferences.bearerToken}")
                .build()
        )
    }
}


class TokenAuthenticator : Authenticator, KoinComponent {
    val authServices: AuthServices by inject()
    val sharedPreferences: SecureSharedPreferences by inject()
    val setFirebaseTokenUseCase: SetFirebaseTokenUseCase by inject()

    override fun authenticate(route: Route?, response: Response): Request? {
        // Refresh your access_token using a synchronous api request
        var newAccessToken: String? = null

        val phonePair = sharedPreferences.phoneNumber

        if(phonePair != null && phonePair.phone.isNotBlank()
            && !sharedPreferences.password.isNullOrEmpty()) {
            Timber.d(authServices.toString())
            newAccessToken = authServices.getLoginRefresh(
                phone = sharedPreferences.phoneNumber?.phone.orEmpty(),
                password = sharedPreferences.password.toString()).token.toString()
        }
        sharedPreferences.bearerToken = newAccessToken
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
        // Add new header to rejected request and retry it

        return response.request().newBuilder()
            .header("Authorization", "Bearer ${newAccessToken}")
            .build()
    }

}