package com.example.sunlightdesign.usecase.usercase.accountUse.get


import com.example.sunlightdesign.data.source.AccountRepository
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.UsersList
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase


class AccountUsersListUseCase constructor(
    private val itemsRepository: AccountRepository
) : BaseCoroutinesUseCase<UsersList?>() {

    override suspend fun executeOnBackground(): UsersList? = itemsRepository.getUsersList()
}

