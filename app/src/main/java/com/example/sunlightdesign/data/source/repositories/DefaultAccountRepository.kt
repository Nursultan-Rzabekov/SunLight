package com.example.sunlightdesign.data.source.repositories

import com.example.sunlightdesign.data.source.AccountRepository
import com.example.sunlightdesign.data.source.dataSource.AccountDataSource
import com.example.sunlightdesign.data.source.dataSource.AuthDataSource
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.CountriesList
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.UsersList
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
}
