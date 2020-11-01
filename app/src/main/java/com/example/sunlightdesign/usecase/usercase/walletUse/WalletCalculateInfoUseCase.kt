package com.example.sunlightdesign.usecase.usercase.walletUse

import com.example.sunlightdesign.data.source.WalletRepository
import com.example.sunlightdesign.data.source.dataSource.remote.wallets.entity.CurrencyCalculate
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase

class WalletCalculateInfoUseCase(
    private val repository: WalletRepository
): BaseCoroutinesUseCase<CurrencyCalculate?>() {

    override suspend fun executeOnBackground(): CurrencyCalculate? =
        repository.getCalculateInfo()
}