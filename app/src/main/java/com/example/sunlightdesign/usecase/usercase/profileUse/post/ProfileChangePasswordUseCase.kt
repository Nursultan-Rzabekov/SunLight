package com.example.sunlightdesign.usecase.usercase.profileUse.post

import com.example.sunlightdesign.data.source.ProfileRepository
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.BaseResponse
import com.example.sunlightdesign.data.source.repositories.DefaultProfileRepository
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase

class ProfileChangePasswordUseCase(
    private val repository: ProfileRepository
): BaseCoroutinesUseCase<BaseResponse?>() {

    private var model: ChangePassword? = null

    fun setData(changePassword: ChangePassword){
        this.model = changePassword
    }

    override suspend fun executeOnBackground(): BaseResponse? =
        model?.let { repository.changePassword(it) }
}

data class ChangePassword(
    var old_password: String,
    var password: String,
    var password_confirmation: String
)