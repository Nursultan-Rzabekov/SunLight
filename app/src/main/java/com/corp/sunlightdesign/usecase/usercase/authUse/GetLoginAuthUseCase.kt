package com.corp.sunlightdesign.usecase.usercase.authUse


import com.corp.sunlightdesign.data.source.AuthRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.Login
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase


class GetLoginAuthUseCase constructor(
    private val itemsRepository: AuthRepository
) : BaseCoroutinesUseCase<Login?>() {

    private var model: SetLogin? = null

    fun setData(model: SetLogin) {
        this.model = model
    }

    override suspend fun executeOnBackground(): Login? =
        this.model?.let { itemsRepository.getTasks(it) }
}

data class SetLogin(val phone: String, val password: String)