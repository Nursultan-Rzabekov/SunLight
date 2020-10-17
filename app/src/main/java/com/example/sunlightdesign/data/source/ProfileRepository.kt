package com.example.sunlightdesign.data.source

import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.BaseResponse
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.UserInfo
import com.example.sunlightdesign.usecase.usercase.profileUse.post.ChangePassword

interface ProfileRepository {
    suspend fun getUserInfo(): UserInfo
    suspend fun changePassword(changePassword: ChangePassword): BaseResponse
}