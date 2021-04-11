package com.example.sunlightdesign.utils

import androidx.appcompat.app.AlertDialog
import com.example.sunlightdesign.BuildConfig
import com.example.sunlightdesign.data.source.dataSource.remote.auth.AuthServices
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Login
import com.example.sunlightdesign.usecase.usercase.authUse.SetFirebaseTokenUseCase
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

        if(phonePair != null && phonePair.first.isNotBlank() && phonePair.second.isNotBlank()
            && !sharedPreferences.password.isNullOrEmpty()){
            Timber.d(authServices.toString())
            newAccessToken = authServices.getLoginRefresh(
                phone = sharedPreferences.phoneNumber.toString(),
                password = sharedPreferences.password.toString()).token.toString()
        }
        sharedPreferences.bearerToken = newAccessToken
        sharedPreferences.firebaseToken?.let {
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
        // Add new header to rejected request and retry it

        return response.request().newBuilder()
            .header("Authorization", "Bearer ${newAccessToken}")
            .build()
    }

}