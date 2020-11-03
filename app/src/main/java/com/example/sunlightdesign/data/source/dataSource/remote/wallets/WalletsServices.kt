package com.example.sunlightdesign.data.source.dataSource.remote.wallets

import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.OfficesList
import com.example.sunlightdesign.data.source.dataSource.remote.wallets.entity.CurrencyCalculate
import com.example.sunlightdesign.data.source.dataSource.remote.wallets.entity.Wallet
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface WalletsServices {

    @GET("wallets/detail")
    fun getWalletsInfo(
    ): Deferred<Wallet>

    @GET("withdraws/calculate")
    fun getCalculateInfo(
    ): Deferred<CurrencyCalculate>

    @GET("withdraws/offices-list")
    fun getOfficesList(
    ): Deferred<OfficesList>
}