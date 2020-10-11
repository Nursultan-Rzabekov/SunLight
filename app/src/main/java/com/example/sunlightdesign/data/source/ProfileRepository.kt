package com.example.sunlightdesign.data.source

import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.UserInfo

interface ProfileRepository {
    suspend fun getUserInfo(): UserInfo
}