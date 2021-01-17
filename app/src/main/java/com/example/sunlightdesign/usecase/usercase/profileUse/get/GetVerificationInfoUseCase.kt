package com.example.sunlightdesign.usecase.usercase.profileUse.get

import com.example.sunlightdesign.data.source.ProfileRepository
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.VerificationResponse
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase

class GetVerificationInfoUseCase(
    private val repository: ProfileRepository
): BaseCoroutinesUseCase<VerificationResponse>() {

    override suspend fun executeOnBackground(): VerificationResponse =
        repository.getVerificationInfo()

}