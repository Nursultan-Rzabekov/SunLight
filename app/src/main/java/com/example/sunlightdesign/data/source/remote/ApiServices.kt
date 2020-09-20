package com.example.sunlightdesign.data.source.remote

import com.example.sunlightdesign.data.source.remote.entity.CountriesList
import com.example.sunlightdesign.data.source.remote.entity.LoginResponse
import com.example.sunlightdesign.data.source.remote.entity.UsersList
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.http.*


interface ApiServices {

    @GET("/auth/login")
    fun getLoginAuth(): Deferred<List<LoginResponse>>


    @GET("/cabinet/add-partner/users-list")
    fun getUsersList(): Deferred<List<UsersList>>


    @GET("/helper/register")
    fun getListCountriesRegionsCities(): Deferred<List<CountriesList>>

    @FormUrlEncoded
    @POST("/cabinet/add-partner")
    fun addPartnerStepOne(
        @Header("Authorization") bearerToken: String?,
        @Field("access_token") authKey: String?
    ): Deferred<List<LoginResponse>>

}