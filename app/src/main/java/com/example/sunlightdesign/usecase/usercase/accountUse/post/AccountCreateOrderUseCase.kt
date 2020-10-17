package com.example.sunlightdesign.usecase.usercase.accountUse.post


import com.example.sunlightdesign.data.source.AccountRepository
import com.example.sunlightdesign.data.source.dataSource.AddPartner
import com.example.sunlightdesign.data.source.dataSource.CreateOrderPartner
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.AddPartnerResponse
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Login
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Product
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase


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