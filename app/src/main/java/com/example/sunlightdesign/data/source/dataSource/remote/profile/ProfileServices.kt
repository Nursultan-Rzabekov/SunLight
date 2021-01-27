package com.example.sunlightdesign.data.source.dataSource.remote.profile

import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.BaseResponse
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.*
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

    @GET("profile/mobile/recent-invitees")
    fun getInvites(
        @Query("page") page: Int
    ): Deferred<InvitedResponse>

    @GET("helper/verify")
    fun getHelpersForVerification(
    ): Deferred<VerificationHelperResponse>

    @GET("profile/verify")
    fun getVerificationInfo(
    ): Deferred<VerificationResponse>

    @Multipart
    @POST("profile/verify-mobile/0")
    fun verifyUser(
        @Part("user_id") user_id: RequestBody,
        @Part("name") name: RequestBody,
        @Part("surname") surname: RequestBody,
        @Part("middle_name") middle_name: RequestBody,
        @Part("iin") iin: RequestBody,
        @Part("social_status") social_status: RequestBody,
        @Part("bank") bank: RequestBody,
        @Part("iban") iban: RequestBody,
        @Part("type") type: RequestBody,
        @Part("ip") ip: RequestBody?,
        @Part images: List<MultipartBody.Part>
    ): Deferred<VerificationResponse>

}