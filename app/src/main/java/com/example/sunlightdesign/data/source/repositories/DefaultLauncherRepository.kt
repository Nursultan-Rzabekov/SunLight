package com.example.sunlightdesign.data.source.repositories

import com.example.sunlightdesign.data.source.LauncherRepository
import com.example.sunlightdesign.data.source.dataSource.AuthDataSource
import com.example.sunlightdesign.data.source.dataSource.remote.main.MainServices
import com.example.sunlightdesign.utils.SecureSharedPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


class DefaultLauncherRepository constructor(
    private val mainServices: MainServices
) : LauncherRepository {

    override suspend fun getBanners()  = mainServices.getBanners().await()

    override suspend fun getCategories() = mainServices.getCategories().await()

    override suspend fun getPosts() = mainServices.getPosts().await()

}
