
package com.example.sunlightdesign.data.source.remote.auth

import androidx.lifecycle.MutableLiveData
import com.example.sunlightdesign.data.Task
import com.example.sunlightdesign.data.source.AuthDataSource
import com.example.sunlightdesign.data.source.remote.auth.entity.LoginResponse
import com.example.sunlightdesign.data.source.remote.auth.AuthServices
import kotlinx.coroutines.delay


class AuthRemoteDataSource(private val apiServices: AuthServices) : AuthDataSource {

    private val SERVICE_LATENCY_IN_MILLIS = 2000L

    private var TASKS_SERVICE_DATA = LinkedHashMap<String, Task>(2)


    private val observableTasks = MutableLiveData<List<Task>>()

    override suspend fun getTasks(): LoginResponse {
        // Simulate network by delaying the execution.
//        val tasks = TASKS_SERVICE_DATA.values.toList()

        val tasks = apiServices.getLoginAuth().await()
        delay(SERVICE_LATENCY_IN_MILLIS)
        return tasks
    }

    private fun addTask(title: String, description: String) {
        val newTask = Task(title, description)
        TASKS_SERVICE_DATA[newTask.id] = newTask
    }

}
