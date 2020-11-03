package com.example.sunlightdesign.data.source.repositories

import com.example.sunlightdesign.data.source.WalletRepository
import com.example.sunlightdesign.data.source.dataSource.AuthDataSource
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.OfficesList
import com.example.sunlightdesign.data.source.dataSource.remote.wallets.WalletsServices
import com.example.sunlightdesign.data.source.dataSource.remote.wallets.entity.CurrencyCalculate
import com.example.sunlightdesign.data.source.dataSource.remote.wallets.entity.Wallet
import com.example.sunlightdesign.utils.SecureSharedPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


class DefaultWalletRepository constructor(
    private val walletsServices: WalletsServices
) : WalletRepository {

    override suspend fun getMyWallets(): Wallet = walletsServices.getWalletsInfo().await()

    override suspend fun getCalculateInfo(): CurrencyCalculate = walletsServices.getCalculateInfo().await()

    override suspend fun getOffices(): OfficesList = walletsServices.getOfficesList().await()
}
