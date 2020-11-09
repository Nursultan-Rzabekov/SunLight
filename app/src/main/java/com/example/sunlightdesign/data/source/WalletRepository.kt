package com.example.sunlightdesign.data.source

import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.OfficesList
import com.example.sunlightdesign.data.source.dataSource.remote.wallets.entity.CurrencyCalculate
import com.example.sunlightdesign.data.source.dataSource.remote.wallets.entity.Wallet

/**
 * Interface to the data layer.
 */
interface WalletRepository {
    suspend fun getMyWallets(): Wallet
    suspend fun getCalculateInfo(): CurrencyCalculate
    suspend fun getOffices(): OfficesList
}
