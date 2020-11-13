package com.example.sunlightdesign.usecase.usercase.mainUse

import com.example.sunlightdesign.data.source.LauncherRepository
import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.Categories
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase

class GetMainCategoriesUseCase constructor(
    private val launcherRepository: LauncherRepository
) : BaseCoroutinesUseCase<Categories>() {
    override suspend fun executeOnBackground(): Categories = launcherRepository.getCategories()
}