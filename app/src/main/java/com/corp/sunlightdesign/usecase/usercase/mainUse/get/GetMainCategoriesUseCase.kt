package com.corp.sunlightdesign.usecase.usercase.mainUse.get

import com.corp.sunlightdesign.data.source.LauncherRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.main.entity.Categories
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase

class GetMainCategoriesUseCase constructor(
    private val launcherRepository: LauncherRepository
) : BaseCoroutinesUseCase<Categories>() {
    override suspend fun executeOnBackground(): Categories = launcherRepository.getCategories()
}