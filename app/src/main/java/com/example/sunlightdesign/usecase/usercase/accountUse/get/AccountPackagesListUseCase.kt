package com.example.sunlightdesign.usecase.usercase.accountUse.get


import com.example.sunlightdesign.data.source.AccountRepository
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.PackagesList
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase


class AccountPackagesListUseCase constructor(
    private val itemsRepository: AccountRepository
) : BaseCoroutinesUseCase<PackagesList?>() {

    override suspend fun executeOnBackground(): PackagesList? = itemsRepository.getPackagesList()
}

