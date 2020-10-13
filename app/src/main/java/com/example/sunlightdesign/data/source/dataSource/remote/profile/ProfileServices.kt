package com.example.sunlightdesign.data.source.dataSource.remote.profile

import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.UserInfo
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ProfileServices {

    @GET("profile/mobile/me")
    fun getProfileInfo(
    ): Deferred<UserInfo>
}