package com.corp.sunlightdesign.usecase.usercase.authUse

import com.corp.sunlightdesign.data.source.AuthRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.BaseResponse
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase

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