package com.corp.sunlightdesign.usecase.usercase.accountUse.get


import com.corp.sunlightdesign.data.source.AccountRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.UsersList
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase


class AccountUsersListUseCase constructor(
    private val itemsRepository: AccountRepository
) : BaseCoroutinesUseCase<UsersList?>() {

    override suspend fun executeOnBackground(): UsersList? = itemsRepository.getUsersList()
}

