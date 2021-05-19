package com.corp.sunlightdesign.usecase.usercase.accountUse.post


import com.corp.sunlightdesign.data.source.AccountRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.User
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase


class AccountSetPackagesUseCase constructor(
    private val itemsRepository: AccountRepository
) : BaseCoroutinesUseCase<User?>() {

    private var model: SetPackage? = null

    fun setData(setPackage: SetPackage) {
        this.model = setPackage
    }

    override suspend fun executeOnBackground(): User? =
        this.model?.let { itemsRepository.setPackage(package_id = it.packageId, user_id = it.userId) }
}

data class SetPackage(
    var userId: Int,
    var packageId: Int
)

