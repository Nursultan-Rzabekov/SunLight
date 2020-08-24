

package com.example.sunlightdesign.data.source

import androidx.lifecycle.LiveData
import com.example.sunlightdesign.data.Result
import com.example.sunlightdesign.data.Task

/**
 * Interface to the data layer.
 */
interface TasksRepository {

    fun observeTasks(): LiveData<Result<List<Task>>>

    suspend fun getTasks(forceUpdate: Boolean = false): Result<List<Task>>

    suspend fun refreshTasks()

    fun observeTask(taskId: String): LiveData<Result<Task>>

    suspend fun getTask(taskId: String, forceUpdate: Boolean = false): Result<Task>

    suspend fun refreshTask(taskId: String)

    suspend fun deleteTask(taskId: String)
}
