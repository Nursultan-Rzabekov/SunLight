package com.example.sunlightdesign.data.source.dataSource.remote.wallets

import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.OfficesList
import com.example.sunlightdesign.data.source.dataSource.remote.wallets.entity.WithdrawalReceipt
import com.example.sunlightdesign.data.source.dataSource.remote.wallets.entity.CurrencyCalculate
import com.example.sunlightdesign.data.source.dataSource.remote.wallets.entity.Wallet
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

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

    @POST("withdraws/store")
    fun storeWithdrawal(
        @Query("bv_value") bvValue: Int,
        @Query("cash_amount") cashAmount: Int,
        @Query("currency_id") currencyId: Int,
        @Query("currency_value") currencyValue: Int,
        @Query("office_id") officeId: Int,
        @Query("user_id") userId: Int,
        @Query("withdraw_type") withdrawType: Int
    ): Deferred<WithdrawalReceipt>
}