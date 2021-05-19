package com.corp.sunlightdesign.usecase.usercase.profileUse.get

import com.corp.sunlightdesign.data.source.ProfileRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.profile.entity.UserInfo
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase

class ProfileInfoUseCase(
    private val profileRepository: ProfileRepository
) : BaseCoroutinesUseCase<UserInfo?>() {

    override suspend fun executeOnBackground(): UserInfo? = profileRepository.getUserInfo()
}