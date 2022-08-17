package com.corp.sunlightdesign.data.source.repositories

import com.corp.sunlightdesign.data.source.WalletRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.OfficesList
import com.corp.sunlightdesign.data.source.dataSource.remote.wallets.WalletsServices
import com.corp.sunlightdesign.data.source.dataSource.remote.wallets.entity.WithdrawalReceipt
import com.corp.sunlightdesign.data.source.dataSource.remote.wallets.entity.CurrencyCalculate
import com.corp.sunlightdesign.data.source.dataSource.remote.wallets.entity.Wallet
import com.corp.sunlightdesign.usecase.usercase.walletUse.post.SetWithdrawal


class DefaultWalletRepository constructor(
    private val walletsServices: WalletsServices
) : WalletRepository {

    override suspend fun getMyWallets(): Wallet = walletsServices.getWalletsInfo().await()

    override suspend fun getCalculateInfo(): CurrencyCalculate = walletsServices.getCalculateInfo().await()

    override suspend fun getOffices(): OfficesList = walletsServices.getOfficesList().await()

    override suspend fun storeWithdrawal(setWithdrawal: SetWithdrawal): WithdrawalReceipt =
        walletsServices.storeWithdrawal(
            bvValue = setWithdrawal.bvValue,
            cashAmount = setWithdrawal.cashAmount,
            currencyId = setWithdrawal.currencyId,
            currencyValue = setWithdrawal.currencyValue,
            officeId = setWithdrawal.officeId,
            userId = setWithdrawal.userId,
            withdrawType = setWithdrawal.withdrawType
        ).await()
}