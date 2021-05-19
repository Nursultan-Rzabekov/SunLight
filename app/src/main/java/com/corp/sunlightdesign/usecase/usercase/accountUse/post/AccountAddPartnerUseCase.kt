package com.corp.sunlightdesign.usecase.usercase.accountUse.post


import com.corp.sunlightdesign.data.source.AccountRepository
import com.corp.sunlightdesign.data.source.dataSource.AddPartner
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.Login
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase


class AccountAddPartnerUseCase constructor(
    private val itemsRepository: AccountRepository
) : BaseCoroutinesUseCase<Login?>() {

    private var model: AddPartner? = null

    fun setData(model: AddPartner) {
        this.model = model
    }

    override suspend fun executeOnBackground(): Login? =
        this.model?.let { itemsRepository.addPartner(it) }
}

