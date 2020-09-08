
package com.example.sunlightdesign.data.source

import androidx.lifecycle.LiveData
import com.example.sunlightdesign.data.Task

/**
 * Main entry point for accessing tasks data.
 */
interface TasksDataSource {

    suspend fun getTasks(): List<Task>
}
