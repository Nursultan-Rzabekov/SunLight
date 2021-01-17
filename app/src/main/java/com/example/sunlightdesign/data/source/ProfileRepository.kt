package com.example.sunlightdesign.data.source

import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.BaseResponse
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.*
import com.example.sunlightdesign.usecase.usercase.profileUse.post.ChangePassword
import com.example.sunlightdesign.usecase.usercase.profileUse.post.VerificationRequest
import okhttp3.MultipartBody

interface ProfileRepository {
    suspend fun getUserInfo(): UserInfo
    suspend fun changePassword(changePassword: ChangePassword): BaseResponse
    suspend fun changeAvatar(changeAvatar: MultipartBody.Part): ChangeAvatar
    suspend fun getInvites(page: Int): InvitedResponse
    suspend fun getVerifyHelpers(): VerificationHelperResponse
    suspend fun getVerificationInfo(): VerificationResponse
    suspend fun verifyUser(request: VerificationRequest): VerificationResponse
}