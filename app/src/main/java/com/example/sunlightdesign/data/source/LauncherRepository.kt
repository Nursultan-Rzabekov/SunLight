package com.example.sunlightdesign.data.source

import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.Banners
import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.Categories
import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.Post

/**
 * Interface to the data layer.
 */
interface LauncherRepository {

    suspend fun getBanners(): Banners
    suspend fun getCategories(): Categories
    suspend fun getPosts(): Post
}
