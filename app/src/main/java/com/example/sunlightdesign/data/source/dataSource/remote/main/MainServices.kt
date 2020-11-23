package com.example.sunlightdesign.data.source.dataSource.remote.main

import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.*
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MainServices {

    @GET("helper/banners")
    fun getBanners(): Deferred<Banners>

    @GET("helper/categories")
    fun getCategories(): Deferred<Categories>

    @GET("helper/posts")
    fun getPosts(): Deferred<Posts>

    @GET("helper/posts/")
    fun getPostsByCategoryId(
        @Query("category_id") category_id: Int
    ): Deferred<Posts>

    @GET("helper/post/{id}")
    fun showPostDetails(
        @Path("id") id: Int
    ): Deferred<SinglePost>

    @GET("mobile/structure")
    fun getStructureInfo(
    ): Deferred<StructureInfo>

}