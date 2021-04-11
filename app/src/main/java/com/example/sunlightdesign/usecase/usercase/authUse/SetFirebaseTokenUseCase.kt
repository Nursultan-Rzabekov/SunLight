package com.example.sunlightdesign.usecase.usercase.authUse

import com.example.sunlightdesign.data.source.AuthRepository
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.BaseResponse
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase

class SetFirebaseTokenUseCase(
    private val repository: AuthRepository
): BaseCoroutinesUseCase<BaseResponse?>() {

    private var model: String? = null

    fun setModel(model: String) {
        this.model = model
    }

    override suspend fun executeOnBackground(): BaseResponse? =
        this.model?.let { repository.setFirebaseToken(it) }
}