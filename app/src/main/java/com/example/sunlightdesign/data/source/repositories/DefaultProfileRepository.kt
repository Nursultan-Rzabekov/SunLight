package com.example.sunlightdesign.data.source.repositories

import com.example.sunlightdesign.data.source.ProfileRepository
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.BaseResponse
import com.example.sunlightdesign.data.source.dataSource.remote.profile.ProfileServices
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.ChangeAvatar
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.UserInfo
import com.example.sunlightdesign.usecase.usercase.profileUse.post.ChangePassword
import okhttp3.MultipartBody

class DefaultProfileRepository(
    private val services: ProfileServices
) : ProfileRepository {
    override suspend fun getUserInfo(): UserInfo = services.getProfileInfo().await()
    override suspend fun changePassword(changePassword: ChangePassword): BaseResponse
            = services.changePassword(changePassword).await()

    override suspend fun changeAvatar(changeAvatar: MultipartBody.Part): ChangeAvatar
            = services.changeAvatar(changeAvatar).await()
}