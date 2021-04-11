package com.example.sunlightdesign.data.source.dataSource.remote.auth

import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.BaseResponse
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Login
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query


interface AuthServices {

    @FormUrlEncoded
    @POST("auth/login")
    fun getLoginAuth(
        @Field("phone") phone: String,
        @Field("password") password: String
    ): Deferred<Login>

    @FormUrlEncoded
    @POST("auth/login")
    fun getLoginRefresh(
        @Field("phone") phone: String,
        @Field("password") password: String
    ): Login

    @POST("profile/firebase-save-token")
    fun setFirebaseToken(
        @Query("token") firebaseToken: String
    ): Deferred<BaseResponse>
}