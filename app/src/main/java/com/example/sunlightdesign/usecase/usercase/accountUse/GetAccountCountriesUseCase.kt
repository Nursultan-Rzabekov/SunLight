package com.example.sunlightdesign.usecase.usercase.accountUse


import com.example.sunlightdesign.data.source.AccountRepository
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.CountriesList
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Login
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase



class GetAccountCountriesUseCase  constructor(
    private val itemsRepository: AccountRepository
) : BaseCoroutinesUseCase<CountriesList?>() {

    override suspend fun executeOnBackground(): CountriesList? = itemsRepository.getCountriesList()
}

