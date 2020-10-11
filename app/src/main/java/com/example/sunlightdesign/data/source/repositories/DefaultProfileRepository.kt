package com.example.sunlightdesign.data.source.repositories

import com.example.sunlightdesign.data.source.ProfileRepository
import com.example.sunlightdesign.data.source.dataSource.remote.profile.ProfileServices
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.UserInfo

class DefaultProfileRepository(
    private val services: ProfileServices
): ProfileRepository {
    override suspend fun getUserInfo(): UserInfo = services.getProfileInfo().await()
}