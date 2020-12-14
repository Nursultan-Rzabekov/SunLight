package com.example.sunlightdesign.usecase.usercase.profileUse.get

import com.example.sunlightdesign.data.source.ProfileRepository
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.InvitedResponse
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase

class GetInvitesUseCase(
    private val profileRepository: ProfileRepository
): BaseCoroutinesUseCase<InvitedResponse?>() {

    private var page: Int? = null

    fun setPage(page: Int) {
        this.page = page
    }

    override suspend fun executeOnBackground(): InvitedResponse? =
        this.page?.let {
            profileRepository.getInvites(it)
        }
}