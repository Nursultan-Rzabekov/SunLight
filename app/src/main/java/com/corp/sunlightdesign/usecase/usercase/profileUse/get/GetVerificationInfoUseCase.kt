package com.corp.sunlightdesign.usecase.usercase.profileUse.get

import com.corp.sunlightdesign.data.source.ProfileRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.profile.entity.VerificationResponse
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase

class GetVerificationInfoUseCase(
    private val repository: ProfileRepository
): BaseCoroutinesUseCase<VerificationResponse>() {

    override suspend fun executeOnBackground(): VerificationResponse =
        repository.getVerificationInfo()

}