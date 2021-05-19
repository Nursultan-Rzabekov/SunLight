package com.corp.sunlightdesign.usecase.usercase.walletUse.get

import com.corp.sunlightdesign.data.source.WalletRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.wallets.entity.CurrencyCalculate
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase

class WalletCalculateInfoUseCase(
    private val repository: WalletRepository
): BaseCoroutinesUseCase<CurrencyCalculate?>() {

    override suspend fun executeOnBackground(): CurrencyCalculate? =
        repository.getCalculateInfo()
}