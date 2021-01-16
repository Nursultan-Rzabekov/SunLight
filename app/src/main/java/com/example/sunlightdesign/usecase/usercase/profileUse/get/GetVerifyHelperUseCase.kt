package com.example.sunlightdesign.usecase.usercase.profileUse.get

import com.example.sunlightdesign.data.source.ProfileRepository
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.VerificationHelperResponse
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase

class GetVerifyHelperUseCase(
    private val repository: ProfileRepository
): BaseCoroutinesUseCase<VerificationHelperResponse>() {

    override suspend fun executeOnBackground(): VerificationHelperResponse =
        repository.getVerifyHelpers()
}