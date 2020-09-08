package com.example.sunlightdesign.data.source.remote

import com.example.sunlightdesign.data.Task
import kotlinx.coroutines.Deferred
import retrofit2.http.GET


interface ApiServices {

    @GET("/en/web/good-radio-online/api-get-category")
    fun getListAsync(): Deferred<List<Task>>
}