package com.corp.sunlightdesign.usecase.usercase.accountUse.post


import com.corp.sunlightdesign.data.source.AccountRepository
import com.corp.sunlightdesign.data.source.dataSource.CreateOrderPartner
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.AddPartnerResponse
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase


class AccountCreateOrderUseCase constructor(
    private val itemsRepository: AccountRepository
) : BaseCoroutinesUseCase<AddPartnerResponse?>() {

    private var model: CreateOrderPartner? = null

    fun setData(model: CreateOrderPartner) {
        this.model = model
    }

    override suspend fun executeOnBackground(): AddPartnerResponse? =
        this.model?.let { itemsRepository.createOrder(it) }
}