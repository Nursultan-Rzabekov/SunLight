package com.example.sunlightdesign.data.source.dataSource.remote.account

import com.example.sunlightdesign.data.source.dataSource.AddPartner
import com.example.sunlightdesign.data.source.dataSource.CreateOrderPartner
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.*
import kotlinx.coroutines.Deferred
import retrofit2.http.*


interface AccountServices {

    @GET("cabinet/add-partner/users-list")
    fun getUsersList(
    ): Deferred<UsersList>

    @GET("helper/register")
    fun getListCountriesRegionsCities(
    ): Deferred<CountriesList>

    @POST("cabinet/add-partner")
    fun addPartnerStepOne(
        @Body addPartner: AddPartner
    ): Deferred<Login>

    @GET("cabinet/add-partner/packages-list")
    fun getPackagesList(
    ): Deferred<PackagesList>

    @POST("cabinet/set-package")
    fun addPartnerStepTwo(
        @Query("package_id") package_id: Int,
        @Query("user_id") user_id: Int
    ): Deferred<User>

    @GET("cabinet/add-partner/packages-list")
    fun getPackagesIdList(
        @Query("package_id") package_id: Int
    ): Deferred<List<AddPartnerPackagesList>>

    @GET("cabinet/add-partner/offices-list")
    fun getOfficesList(
    ): Deferred<OfficesList>

    @POST("cabinet/add-partner")
    fun createOrder(
        @Body createOrderPartner: CreateOrderPartner
    ): Deferred<AddPartnerResponse>

}