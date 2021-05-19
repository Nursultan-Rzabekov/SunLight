package com.corp.sunlightdesign.usecase.usercase.walletUse.post

import com.corp.sunlightdesign.data.source.WalletRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.wallets.entity.WithdrawalReceipt
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase

class WalletStoreWithdrawalUseCase(
    private val walletRepository: WalletRepository
) : BaseCoroutinesUseCase<WithdrawalReceipt?>() {

    private var model: SetWithdrawal? = null

    fun setData(model: SetWithdrawal) {
        this.model = model
    }

    override suspend fun executeOnBackground(): WithdrawalReceipt? =
        this.model?.let { walletRepository.storeWithdrawal(it) }
}

data class SetWithdrawal(
    val bvValue: Int,
    val cashAmount: Double,
    val currencyId: Int,
    val currencyValue: Double,
    val officeId: Int,
    val userId: Int,
    val withdrawType: Int
)

