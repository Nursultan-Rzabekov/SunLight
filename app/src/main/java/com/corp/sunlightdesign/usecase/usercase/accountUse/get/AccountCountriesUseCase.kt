package com.corp.sunlightdesign.usecase.usercase.accountUse.get


import com.corp.sunlightdesign.data.source.AccountRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.CountriesList
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase


class AccountCountriesUseCase constructor(
    private val itemsRepository: AccountRepository
) : BaseCoroutinesUseCase<CountriesList?>() {

    override suspend fun executeOnBackground(): CountriesList? = itemsRepository.getCountriesList()
}

