package com.example.sunlightdesign.data.source.dataSource.remote.account

import com.example.sunlightdesign.data.source.dataSource.AddPartner
import com.example.sunlightdesign.data.source.dataSource.CreateOrderPartner
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.*
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


interface AccountServices {

    @GET("cabinet/add-partner/users-list")
    fun getUsersList(
    ): Deferred<UsersList>

    @GET("helper/register")
    fun getListCountriesRegionsCities(
    ): Deferred<CountriesList>

    @Multipart
    @POST("cabinet/add-partner")
    fun addPartnerStepOne(
        @Part("first_name") first_name: RequestBody,
        @Part("last_name") last_name: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("middle_name") middle_name: RequestBody,
        @Part("country_id") country_id: RequestBody,
        @Part("region_id") region_id: RequestBody,
        @Part("city_id") city_id: RequestBody,
        @Part("iin") iin: RequestBody,
        @Part("register_by") register_by: RequestBody,
        @Part("position") position: RequestBody?,
        @Part document_front_path: MultipartBody.Part,
        @Part document_back_path: MultipartBody.Part
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

    @POST("cabinet/create-order")
    fun createOrder(
        @Body createOrderPartner: CreateOrderPartner
    ): Deferred<AddPartnerResponse>

}