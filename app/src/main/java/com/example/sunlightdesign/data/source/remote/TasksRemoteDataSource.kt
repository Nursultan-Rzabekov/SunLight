
package com.example.sunlightdesign.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.sunlightdesign.data.Task
import com.example.sunlightdesign.data.source.TasksDataSource
import kotlinx.coroutines.delay

/**
 * Implementation of the data source that adds a latency simulating network.
 */
object TasksRemoteDataSource : TasksDataSource {

    private const val SERVICE_LATENCY_IN_MILLIS = 2000L

    private var TASKS_SERVICE_DATA = LinkedHashMap<String, Task>(2)


    private val observableTasks = MutableLiveData<List<Task>>()

    override suspend fun getTasks(): List<Task> {
        // Simulate network by delaying the execution.
        val tasks = TASKS_SERVICE_DATA.values.toList()
        delay(SERVICE_LATENCY_IN_MILLIS)
        return tasks
    }


    private fun addTask(title: String, description: String) {
        val newTask = Task(title, description)
        TASKS_SERVICE_DATA[newTask.id] = newTask
    }


}
