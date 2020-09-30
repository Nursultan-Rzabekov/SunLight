package com.example.sunlightdesign.data.source.dataSource.remote.auth

import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.*
import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import retrofit2.http.*


interface AuthServices {

    @POST("auth/login")
    fun getLoginAuth(
        @Field("phone") phone: String,
        @Field("password") password: String
    ): Deferred<Login>

    @GET("/refreshToken")
    fun refreshToken(): Login
}