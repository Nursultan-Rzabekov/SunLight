package com.example.sunlightdesign.usecase.usercase.authUse


import com.example.sunlightdesign.data.source.AuthRepository
import com.example.sunlightdesign.data.source.remote.auth.entity.LoginResponse
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase



class GetLoginAuthUseCase  constructor(
    private val itemsRepository: AuthRepository
) : BaseCoroutinesUseCase<LoginResponse?>() {

    private var model: SetLogin? = null

    fun setData(model: SetLogin) {
        this.model = model
    }

    override suspend fun executeOnBackground(): LoginResponse? =
        this.model?.let { itemsRepository.getTasks(it) }
}

data class SetLogin(val phone: String,val password: String)