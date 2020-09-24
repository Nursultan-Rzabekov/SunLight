package com.example.sunlightdesign.utils

import com.example.sunlightdesign.data.source.dataSource.remote.auth.AuthServices
import okhttp3.*
import org.koin.core.KoinComponent
import org.koin.core.inject

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

    override fun authenticate(route: Route?, response: Response): Request? {
        // Refresh your access_token using a synchronous api request
        val newAccessToken = authServices.refreshToken()
        sharedPreferences.bearerToken = newAccessToken.token
        // Add new header to rejected request and retry it
        return response.request().newBuilder()
            .header("Authorization", "Bearer ${newAccessToken.token}")
            .build()
    }

}