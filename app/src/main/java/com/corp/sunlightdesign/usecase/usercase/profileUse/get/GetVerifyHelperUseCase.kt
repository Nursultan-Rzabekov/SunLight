package com.corp.sunlightdesign.usecase.usercase.profileUse.get

import com.corp.sunlightdesign.data.source.ProfileRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.profile.entity.VerificationHelperResponse
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase

class GetVerifyHelperUseCase(
    private val repository: ProfileRepository
): BaseCoroutinesUseCase<VerificationHelperResponse>() {

    override suspend fun executeOnBackground(): VerificationHelperResponse =
        repository.getVerifyHelpers()
}