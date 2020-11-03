package com.example.sunlightdesign.usecase.usercase.walletUse.get

import com.example.sunlightdesign.data.source.WalletRepository
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.OfficesList
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase

class WalletGetOfficesUseCase(
    private val walletRepository: WalletRepository
) : BaseCoroutinesUseCase<OfficesList>() {

    override suspend fun executeOnBackground(): OfficesList =
        walletRepository.getOffices()
}