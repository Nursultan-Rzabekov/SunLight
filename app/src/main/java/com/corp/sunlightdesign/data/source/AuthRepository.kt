package com.corp.sunlightdesign.data.source

import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.BaseResponse
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.Login
import com.corp.sunlightdesign.usecase.usercase.authUse.SetLogin

/**
 * Interface to the data layer.
 */
interface AuthRepository {

    suspend fun getTasks(model: SetLogin): Login

    suspend fun setFirebaseToken(token: String): BaseResponse
}
