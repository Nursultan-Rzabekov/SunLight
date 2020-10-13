package com.example.sunlightdesign.usecase.usercase.walletUse

import com.example.sunlightdesign.data.source.WalletRepository
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.UserInfo
import com.example.sunlightdesign.data.source.dataSource.remote.wallets.entity.Wallet
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase

class WalletInfoUseCase(
    private val walletRepository: WalletRepository
) : BaseCoroutinesUseCase<Wallet?>() {

    override suspend fun executeOnBackground(): Wallet? = walletRepository.getMyWallets()
}