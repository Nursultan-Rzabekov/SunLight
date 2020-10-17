package com.example.sunlightdesign.usecase.usercase.profileUse.get

import com.example.sunlightdesign.data.source.ProfileRepository
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.UserInfo
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase

class ProfileInfoUseCase(
    private val profileRepository: ProfileRepository
) : BaseCoroutinesUseCase<UserInfo?>() {

    override suspend fun executeOnBackground(): UserInfo? = profileRepository.getUserInfo()
}