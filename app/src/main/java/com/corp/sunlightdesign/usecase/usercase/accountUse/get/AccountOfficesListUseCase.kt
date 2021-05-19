package com.corp.sunlightdesign.usecase.usercase.accountUse.get


import com.corp.sunlightdesign.data.source.AccountRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.OfficesList
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase


class AccountOfficesListUseCase constructor(
    private val itemsRepository: AccountRepository
) : BaseCoroutinesUseCase<OfficesList?>() {

    override suspend fun executeOnBackground(): OfficesList? = itemsRepository.getOfficesList()
}

