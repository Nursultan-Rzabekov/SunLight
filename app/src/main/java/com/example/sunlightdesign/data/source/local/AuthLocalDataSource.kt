
package com.example.sunlightdesign.data.source.local

import com.example.sunlightdesign.data.source.AuthDataSource
import com.example.sunlightdesign.data.source.remote.auth.entity.LoginResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


class AuthLocalDataSource internal constructor(
    private val tasksDao: AuthDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : AuthDataSource {

    override suspend fun getTasks(): LoginResponse {
        TODO()
    }
}
