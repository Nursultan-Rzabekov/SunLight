
package com.example.sunlightdesign.data.source.remote.auth

import android.provider.Settings
import androidx.lifecycle.MutableLiveData
import com.example.sunlightdesign.data.Task
import com.example.sunlightdesign.data.source.AuthDataSource
import com.example.sunlightdesign.data.source.remote.auth.entity.LoginResponse
import com.example.sunlightdesign.data.source.remote.auth.AuthServices
import com.example.sunlightdesign.usecase.usercase.authUse.SetLogin
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.delay
import org.json.JSONException
import org.json.JSONObject


class AuthRemoteDataSource(private val apiServices: AuthServices) : AuthDataSource {

    private val SERVICE_LATENCY_IN_MILLIS = 2000L

    private var TASKS_SERVICE_DATA = LinkedHashMap<String, Task>(2)


    private val observableTasks = MutableLiveData<List<Task>>()

    override suspend fun getTasks(model: SetLogin): LoginResponse {
        // Simulate network by delaying the execution.
//        val tasks = TASKS_SERVICE_DATA.values.toList()

        val tasks = apiServices.getLoginAuth(apiJsonMap(model.phone, model.password)).await()
        delay(SERVICE_LATENCY_IN_MILLIS)
        return tasks
    }

    private fun addTask(title: String, description: String) {
        val newTask = Task(title, description)
        TASKS_SERVICE_DATA[newTask.id] = newTask
    }

    private fun apiJsonMap(phone: String, password: String): JsonObject {
        var gsonObject = JsonObject()
        try {
            val jsonObj = JSONObject()
            jsonObj.put("phone",phone)
            jsonObj.put("password",password)
            val jsonParser = JsonParser()
            gsonObject = jsonParser.parse(jsonObj.toString()) as JsonObject
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return gsonObject
    }

}
