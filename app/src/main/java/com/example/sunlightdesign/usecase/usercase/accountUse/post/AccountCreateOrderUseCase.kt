package com.example.sunlightdesign.usecase.usercase.accountUse.post


import com.example.sunlightdesign.data.source.AccountRepository
import com.example.sunlightdesign.data.source.dataSource.AddPartner
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Login
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase


class AccountCreateOrderUseCase constructor(
    private val itemsRepository: AccountRepository
) : BaseCoroutinesUseCase<Login?>() {

    private var model: AddPartner? = null

    fun setData(model: AddPartner) {
        this.model = model
    }

    override suspend fun executeOnBackground(): Login? =
        this.model?.let { itemsRepository.addPartner(it) }
}

