package com.example.sunlightdesign.data.source.dataSource.remote.wallets

import com.example.sunlightdesign.data.source.dataSource.remote.wallets.entity.Wallet
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface WalletsServices {

    @GET("wallets/detail")
    fun getWalletsInfo(
    ): Deferred<Wallet>
}