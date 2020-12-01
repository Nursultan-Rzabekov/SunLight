package com.example.sunlightdesign.data.source.dataSource.remote.account

import androidx.lifecycle.MutableLiveData
import com.example.sunlightdesign.data.Task
import com.example.sunlightdesign.data.source.dataSource.AccountDataSource
import com.example.sunlightdesign.data.source.dataSource.AddPartner
import com.example.sunlightdesign.data.source.dataSource.CreateOrderPartner
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.*
import okhttp3.MediaType
import okhttp3.RequestBody





class AccountRemoteDataSource(private val apiServices: AccountServices) : AccountDataSource {

    private val observableTasks = MutableLiveData<List<Task>>()

    override suspend fun getCountriesList(): CountriesList =
        apiServices.getListCountriesRegionsCities().await()

    override suspend fun getUsersList(): UsersList = apiServices.getUsersList().await()

    override suspend fun addPartner(body: AddPartner): Login {
        val firstName =
            RequestBody.create(MediaType.parse("multipart/form-data"), body.first_name)
        val lastName =
            RequestBody.create(MediaType.parse("multipart/form-data"), body.last_name)
        val phone =
            RequestBody.create(MediaType.parse("multipart/form-data"), body.phone)
        val middleName =
            RequestBody.create(MediaType.parse("multipart/form-data"), body.middle_name)
        val countryId =
            RequestBody.create(MediaType.parse("multipart/form-data"), body.country_id.toString())
        val regionId =
            RequestBody.create(MediaType.parse("multipart/form-data"), body.region_id.toString())
        val cityId =
            RequestBody.create(MediaType.parse("multipart/form-data"), body.city_id.toString())
        val iin =
            RequestBody.create(MediaType.parse("multipart/form-data"), body.iin)
        val position =
            RequestBody.create(MediaType.parse("multipart/form-data"), body.position)
        val registerBy =
            RequestBody.create(MediaType.parse("multipart/form-data"), body.register_by.toString())


        return apiServices.addPartnerStepOne(
            document_back_path = body.document_back,
            document_front_path = body.document_front,
            first_name = firstName,
            last_name = lastName,
            middle_name = middleName,
            phone = phone,
            iin = iin,
            position = position,
            region_id = regionId,
            register_by = registerBy,
            city_id = cityId,
            country_id = countryId
        ).await()
    }

    override suspend fun setPackage(package_id: Int, user_id: Int): User =
        apiServices.addPartnerStepTwo(package_id = package_id, user_id = user_id).await()

    override suspend fun getPackagesList(): PackagesList = apiServices.getPackagesList().await()

    override suspend fun getOfficesList(): OfficesList = apiServices.getOfficesList().await()

    override suspend fun createOrder(createOrderPartner: CreateOrderPartner): AddPartnerResponse =
        apiServices.createOrder(createOrderPartner).await()
}
