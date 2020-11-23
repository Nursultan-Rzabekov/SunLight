package com.example.sunlightdesign.data.source

import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.*

/**
 * Interface to the data layer.
 */
interface LauncherRepository {

    suspend fun getBanners(): Banners
    suspend fun getCategories(): Categories
    suspend fun getPosts(): Posts
    suspend fun getByCategoryId(id: Int): Posts
    suspend fun getStructureInfo(): StructureInfo
    suspend fun getPostById(id: Int): SinglePost
}
