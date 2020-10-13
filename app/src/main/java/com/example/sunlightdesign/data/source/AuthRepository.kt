package com.example.sunlightdesign.data.source

import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Login
import com.example.sunlightdesign.usecase.usercase.authUse.SetLogin

/**
 * Interface to the data layer.
 */
interface AuthRepository {

    suspend fun getTasks(model: SetLogin): Login
}
