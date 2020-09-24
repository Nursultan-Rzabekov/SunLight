package com.example.sunlightdesign.usecase.usercase.accountUse


import com.example.sunlightdesign.data.source.AccountRepository
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.CountriesList
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Login
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase



class GetAccountUseCase  constructor(
    private val itemsRepository: AccountRepository
) : BaseCoroutinesUseCase<List<CountriesList>?>() {

    override suspend fun executeOnBackground(): List<CountriesList>? = itemsRepository.getCountriesList()
}

