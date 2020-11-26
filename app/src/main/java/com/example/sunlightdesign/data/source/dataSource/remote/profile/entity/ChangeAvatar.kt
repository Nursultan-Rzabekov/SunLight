package com.example.sunlightdesign.data.source.dataSource.remote.profile.entity

import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.BaseResponse

data class ChangeAvatar(
    val user: User
) : BaseResponse()