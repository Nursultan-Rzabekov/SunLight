package com.corp.sunlightdesign.usecase.usercase.accountUse.get


import com.corp.sunlightdesign.data.source.AccountRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.PackagesList
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase


class AccountPackagesListUseCase constructor(
    private val itemsRepository: AccountRepository
) : BaseCoroutinesUseCase<PackagesList?>() {

    override suspend fun executeOnBackground(): PackagesList? = itemsRepository.getPackagesList()
}

