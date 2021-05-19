package com.corp.sunlightdesign.data.source.dataSource.remote.profile.entity

import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.BaseResponse

data class ChangeAvatar(
    val user: User
) : BaseResponse()