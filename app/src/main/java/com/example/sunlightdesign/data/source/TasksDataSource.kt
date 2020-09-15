
package com.example.sunlightdesign.data.source

import com.example.sunlightdesign.data.source.remote.entity.LoginResponse

/**
 * Main entry point for accessing tasks data.
 */
interface TasksDataSource {

    suspend fun getTasks(): List<LoginResponse>
}
