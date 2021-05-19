package com.corp.sunlightdesign.data.source.dataSource

import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.BaseResponse
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.Login
import com.corp.sunlightdesign.usecase.usercase.authUse.SetLogin

interface AuthDataSource {
    suspend fun getTasks(model: SetLogin): Login
    suspend fun setFirebaseToken(token: String): BaseResponse
}
