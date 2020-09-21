
package com.example.sunlightdesign.data.source

import com.example.sunlightdesign.data.source.remote.auth.entity.Login
import com.example.sunlightdesign.usecase.usercase.authUse.SetLogin

interface AuthDataSource {
    suspend fun getTasks(model: SetLogin): Login
}
