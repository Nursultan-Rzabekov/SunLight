
package com.example.sunlightdesign.data.source

import androidx.lifecycle.LiveData
import com.example.sunlightdesign.data.Result
import com.example.sunlightdesign.data.Task

/**
 * Main entry point for accessing tasks data.
 */
interface TasksDataSource {

    fun observeTasks(): LiveData<Result<List<Task>>>

    suspend fun getTasks(): Result<List<Task>>

    suspend fun refreshTasks()

    fun observeTask(taskId: String): LiveData<Result<Task>>

    suspend fun getTask(taskId: String): Result<Task>

    suspend fun saveTask(task: Task)

    suspend fun deleteAllTasks()

    suspend fun deleteTask(taskId: String)
}
