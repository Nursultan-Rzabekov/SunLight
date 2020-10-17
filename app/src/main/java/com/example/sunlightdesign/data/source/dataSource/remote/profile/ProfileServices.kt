package com.example.sunlightdesign.data.source.dataSource.remote.profile

import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.BaseResponse
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.UserInfo
import com.example.sunlightdesign.usecase.usercase.profileUse.post.ChangePassword
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProfileServices {

    @GET("profile/mobile/me")
    fun getProfileInfo(
    ): Deferred<UserInfo>

    @POST("profile/update-password")
    fun changePassword(
        @Body changePassword: ChangePassword
    ): Deferred<BaseResponse>
}