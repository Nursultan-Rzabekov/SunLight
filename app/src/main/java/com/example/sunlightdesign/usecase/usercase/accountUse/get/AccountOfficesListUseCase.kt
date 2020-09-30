package com.example.sunlightdesign.usecase.usercase.accountUse.get


import com.example.sunlightdesign.data.source.AccountRepository
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.CountriesList
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Login
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.OfficesList
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase



class AccountOfficesListUseCase  constructor(
    private val itemsRepository: AccountRepository
) : BaseCoroutinesUseCase<OfficesList?>() {

    override suspend fun executeOnBackground(): OfficesList? = itemsRepository.getOfficesList()
}

