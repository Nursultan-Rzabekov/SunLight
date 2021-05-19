package com.corp.sunlightdesign.usecase.usercase.walletUse.get

import com.corp.sunlightdesign.data.source.WalletRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.wallets.entity.Wallet
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase

class WalletInfoUseCase(
    private val walletRepository: WalletRepository
) : BaseCoroutinesUseCase<Wallet?>() {

    override suspend fun executeOnBackground(): Wallet? = walletRepository.getMyWallets()
}