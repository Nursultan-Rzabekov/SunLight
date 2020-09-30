
package com.example.sunlightdesign.data.source.dataSource.remote.account

import androidx.lifecycle.MutableLiveData
import com.example.sunlightdesign.data.Task
import com.example.sunlightdesign.data.source.dataSource.AccountDataSource
import com.example.sunlightdesign.data.source.dataSource.AddPartner
import com.example.sunlightdesign.data.source.dataSource.AuthDataSource
import com.example.sunlightdesign.data.source.dataSource.CreateOrderPartner
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.*


class AccountRemoteDataSource(private val apiServices: AccountServices) : AccountDataSource
{

    private val observableTasks = MutableLiveData<List<Task>>()

    override suspend fun getCountriesList(): CountriesList = apiServices.getListCountriesRegionsCities().await()

    override suspend fun getUsersList(): UsersList = apiServices.getUsersList().await()

    override suspend fun addPartner(body: AddPartner): Login = apiServices.addPartnerStepOne(body).await()

    override suspend fun setPackage(package_id: Int, user_id: Int): User = apiServices.addPartnerStepTwo(package_id = package_id,user_id = user_id).await()

    override suspend fun getPackagesList(): PackagesList = apiServices.getPackagesList().await()

    override suspend fun getOfficesList(): OfficesList = apiServices.getOfficesList().await()

    override suspend fun createOrder(createOrderPartner: CreateOrderPartner): AddPartnerResponse = apiServices.createOrder(createOrderPartner).await()
}
