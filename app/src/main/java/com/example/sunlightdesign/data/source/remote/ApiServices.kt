package com.example.sunlightdesign.data.source.remote

import com.example.sunlightdesign.data.source.remote.entity.LoginResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET


interface ApiServices {

    @GET("/auth/login")
    fun getLoginAuth(): Deferred<List<LoginResponse>>
}