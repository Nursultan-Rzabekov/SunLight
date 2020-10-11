package com.example.sunlightdesign.data.source.dataSource.remote.profile

import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.UserInfo
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProfileServices {

    @GET("profile/mobile/me")
    fun getProfileInfo(
    ): Deferred<UserInfo>
}