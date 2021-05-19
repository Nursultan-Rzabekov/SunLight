package com.corp.sunlightdesign.usecase.usercase.walletUse.get

import com.corp.sunlightdesign.data.source.WalletRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.OfficesList
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase

class WalletGetOfficesUseCase(
    private val walletRepository: WalletRepository
) : BaseCoroutinesUseCase<OfficesList>() {

    override suspend fun executeOnBackground(): OfficesList =
        walletRepository.getOffices()
}