
package com.example.sunlightdesign.data.source

import com.example.sunlightdesign.data.source.remote.entity.LoginResponse

interface AuthDataSource {
    suspend fun getTasks(): List<LoginResponse>
}
