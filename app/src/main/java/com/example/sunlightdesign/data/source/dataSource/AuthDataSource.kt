
package com.example.sunlightdesign.data.source.dataSource

import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Login
import com.example.sunlightdesign.usecase.usercase.authUse.SetLogin

interface AuthDataSource {
    suspend fun getTasks(model: SetLogin): Login
}
