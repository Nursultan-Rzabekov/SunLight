package com.example.sunlightdesign.data.source.repositories

import com.example.sunlightdesign.data.source.AccountRepository
import com.example.sunlightdesign.data.source.dataSource.AccountDataSource
import com.example.sunlightdesign.data.source.dataSource.AddPartner
import com.example.sunlightdesign.data.source.dataSource.AuthDataSource
import com.example.sunlightdesign.data.source.dataSource.CreateOrderPartner
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.*
import com.example.sunlightdesign.utils.SecureSharedPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


class DefaultAccountRepository constructor(
    private val tasksRemoteDataSource: AccountDataSource,
    private val prefs: SecureSharedPreferences,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AccountRepository {

    override suspend fun getCountriesList(): CountriesList = tasksRemoteDataSource.getCountriesList()

    override suspend fun getUsersList(): UsersList = tasksRemoteDataSource.getUsersList()

    override suspend fun addPartner(addPartner: AddPartner): Login = tasksRemoteDataSource.addPartner(addPartner)

    override suspend fun setPackage(package_id: Int, user_id: Int): User = tasksRemoteDataSource.setPackage(package_id,user_id)

    override suspend fun getPackagesList(): PackagesList = tasksRemoteDataSource.getPackagesList()

    override suspend fun getOfficesList(): OfficesList = tasksRemoteDataSource.getOfficesList()

    override suspend fun createOrder(createOrderPartner: CreateOrderPartner): AddPartnerResponse = tasksRemoteDataSource.createOrder(createOrderPartner)
}
