package com.example.sunlightdesign.data.source.remote.auth

import com.example.sunlightdesign.data.source.remote.auth.entity.*
import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import retrofit2.http.*


interface AuthServices {

    @GET("/auth/login")
    fun getLoginAuth(): Deferred<List<LoginResponse>>


    @GET("/cabinet/add-partner/users-list")
    fun getUsersList(): Deferred<List<UsersList>>


    @GET("/helper/register")
    fun getListCountriesRegionsCities(): Deferred<List<CountriesList>>

    @POST("/cabinet/add-partner")
    fun addPartnerStepOne(
        @Header("Authorization") bearerToken: String?,
        @Body jsonBody: JsonObject
    ): Deferred<List<LoginResponse>>


    @GET("/cabinet/add-partner/packages-list")
    fun getPackagesList(): Deferred<List<PackagesList>>


    @POST("/cabinet/set-package")
    fun addPartnerStepTwo(
        @Header("Authorization") bearerToken: String?,
        @Body jsonBody: JsonObject
    ): Deferred<List<SetPackages>>

    @GET("/cabinet/add-partner/packages-list")
    fun getPackagesIdList(
        @Query("package_id") package_id: Int
    ): Deferred<List<AddPartnerPackagesList>>


    @GET("/cabinet/add-partner/offices-list")
    fun getOfficesList(): Deferred<List<OfficesList>>

    @POST("/cabinet/add-partner")
    fun addPartner(
        @Header("Authorization") bearerToken: String?,
        @Body jsonBody: JsonObject
    ): Deferred<List<AddPartnerResponse>>



}