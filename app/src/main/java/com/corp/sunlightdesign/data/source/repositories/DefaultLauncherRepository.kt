package com.corp.sunlightdesign.data.source.repositories

import com.corp.sunlightdesign.data.source.LauncherRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.main.MainServices
import com.corp.sunlightdesign.data.source.dataSource.remote.main.entity.SinglePost
import com.corp.sunlightdesign.data.source.dataSource.remote.main.entity.StructureInfo


class DefaultLauncherRepository constructor(
    private val mainServices: MainServices
) : LauncherRepository {

    override suspend fun getBanners() = mainServices.getBanners().await()

    override suspend fun getCategories() = mainServices.getCategories().await()

    override suspend fun getPosts() = mainServices.getPosts().await()

    override suspend fun getByCategoryId(id: Int) = mainServices.getPostsByCategoryId(id).await()

    override suspend fun getStructureInfo(): StructureInfo = mainServices.getStructureInfo().await()

    override suspend fun getPostById(id: Int): SinglePost = mainServices.showPostDetails(id).await()

    override suspend fun getCompanyInfo(url: String) = mainServices.getCompanyInfo(url).await()
}
