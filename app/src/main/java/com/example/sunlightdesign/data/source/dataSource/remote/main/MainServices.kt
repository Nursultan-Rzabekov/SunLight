package com.example.sunlightdesign.data.source.dataSource.remote.main

import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.Banners
import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.Categories
import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.Post
import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.PostX
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface MainServices {

    @GET("helper/banners")
    fun getBanners(): Deferred<Banners>

    @GET("helper/categories")
    fun getCategories(): Deferred<Categories>

    @GET("helper/posts")
    fun getPosts(): Deferred<Post>

    @GET("helper/post/{id}")
    fun showPostDetails(
        @Path("id") id: Int
    ): Deferred<PostX>

}