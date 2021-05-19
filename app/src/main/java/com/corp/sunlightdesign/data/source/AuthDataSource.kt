package com.corp.sunlightdesign.data.source

import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.Login
import com.corp.sunlightdesign.usecase.usercase.authUse.SetLogin

interface AuthDataSource {
    suspend fun getTasks(model: SetLogin): Login
}
