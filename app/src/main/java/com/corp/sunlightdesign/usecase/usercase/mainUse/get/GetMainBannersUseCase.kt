package com.corp.sunlightdesign.usecase.usercase.mainUse.get

import com.corp.sunlightdesign.data.source.LauncherRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.main.entity.Banners
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase

class GetMainBannersUseCase constructor(
    private val launcherRepository: LauncherRepository
) : BaseCoroutinesUseCase<Banners>() {

    override suspend fun executeOnBackground(): Banners = launcherRepository.getBanners()
}