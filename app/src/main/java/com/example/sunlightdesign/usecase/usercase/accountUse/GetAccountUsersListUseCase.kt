package com.example.sunlightdesign.usecase.usercase.accountUse


import com.example.sunlightdesign.data.source.AccountRepository
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.CountriesList
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Login
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.UsersList
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase



class GetAccountUsersListUseCase  constructor(
    private val itemsRepository: AccountRepository
) : BaseCoroutinesUseCase<UsersList?>() {

    override suspend fun executeOnBackground(): UsersList? = itemsRepository.getUsersList()
}

