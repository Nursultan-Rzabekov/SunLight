package com.example.sunlightdesign.data.source.repositories

import com.example.sunlightdesign.data.source.AuthDataSource
import com.example.sunlightdesign.data.source.WalletRepository
import com.example.sunlightdesign.data.source.remote.auth.entity.Login

import com.example.sunlightdesign.utils.Prefs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers



class DefaultWalletRepository  constructor(
     private val tasksRemoteDataSource: AuthDataSource,
     private val tasksLocalDataSource: AuthDataSource,
    private val prefs: Prefs,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : WalletRepository {


}
