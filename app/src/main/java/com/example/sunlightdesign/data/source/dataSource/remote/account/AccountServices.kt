package com.example.sunlightdesign.data.source.dataSource.remote.account

import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.*
import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import retrofit2.http.*


interface AccountServices {

    @GET("/cabinet/add-partner/users-list")
    fun getUsersList(
    ): Deferred<List<UsersList>>

    @GET("helper/register")
    fun getListCountriesRegionsCities(
    ): Deferred<List<CountriesList>>

    @POST("cabinet/add-partner")
    fun addPartnerStepOne(
        @Body jsonBody: JsonObject
    ): Deferred<List<Login>>

    @GET("cabinet/add-partner/packages-list")
    fun getPackagesList(
    ): Deferred<List<PackagesList>>

    @POST("cabinet/set-package")
    fun addPartnerStepTwo(
        @Body jsonBody: JsonObject
    ): Deferred<List<SetPackages>>

    @GET("cabinet/add-partner/packages-list")
    fun getPackagesIdList(
        @Query("package_id") package_id: Int
    ): Deferred<List<AddPartnerPackagesList>>

    @GET("cabinet/add-partner/offices-list")
    fun getOfficesList(
    ): Deferred<List<OfficesList>>

    @POST("cabinet/add-partner")
    fun addPartner(
        @Body jsonBody: JsonObject
    ): Deferred<List<AddPartnerResponse>>

}