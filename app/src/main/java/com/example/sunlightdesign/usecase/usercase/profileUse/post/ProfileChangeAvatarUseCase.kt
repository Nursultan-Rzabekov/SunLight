package com.example.sunlightdesign.usecase.usercase.profileUse.post

import com.example.sunlightdesign.data.source.ProfileRepository
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.ChangeAvatar
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase
import okhttp3.MultipartBody

class ProfileChangeAvatarUseCase(
    private val profileRepository: ProfileRepository
) : BaseCoroutinesUseCase<ChangeAvatar?>() {

    var model: MultipartBody.Part? = null

    fun setData(model: MultipartBody.Part) {
        this.model = model
    }

    override suspend fun executeOnBackground(): ChangeAvatar? =
        this.model?.let { profileRepository.changeAvatar(it) }

}