package com.corp.sunlightdesign.data.source

import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.BaseResponse
import com.corp.sunlightdesign.data.source.dataSource.remote.profile.entity.*
import com.corp.sunlightdesign.usecase.usercase.profileUse.post.ChangePassword
import com.corp.sunlightdesign.usecase.usercase.profileUse.post.VerificationRequest
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