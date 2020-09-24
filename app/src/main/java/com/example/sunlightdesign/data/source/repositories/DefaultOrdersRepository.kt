package com.example.sunlightdesign.data.source.repositories

import com.example.sunlightdesign.data.source.dataSource.AuthDataSource
import com.example.sunlightdesign.data.source.OrdersRepository

import com.example.sunlightdesign.utils.SecureSharedPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers



class DefaultOrdersRepository  constructor(
    private val tasksRemoteDataSource: AuthDataSource,
    private val tasksLocalDataSource: AuthDataSource,
    private val prefs: SecureSharedPreferences,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : OrdersRepository {


}
