package com.example.sunlightdesign.data.source.dataSource.remote.profile

import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.BaseResponse
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.ChangeAvatar
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.UserInfo
import com.example.sunlightdesign.usecase.usercase.profileUse.post.ChangePassword
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ProfileServices {

    @GET("profile/mobile/me")
    fun getProfileInfo(
    ): Deferred<UserInfo>

    @POST("profile/update-password")
    fun changePassword(
        @Body changePassword: ChangePassword
    ): Deferred<BaseResponse>

    @Multipart
    @POST("profile/update-avatar")
    fun changeAvatar(
        @Part avatar: MultipartBody.Part
    ): Deferred<ChangeAvatar>
}