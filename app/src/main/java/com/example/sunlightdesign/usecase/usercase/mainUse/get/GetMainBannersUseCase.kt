package com.example.sunlightdesign.usecase.usercase.mainUse.get

import com.example.sunlightdesign.data.source.LauncherRepository
import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.Banners
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase

class GetMainBannersUseCase constructor(
    private val launcherRepository: LauncherRepository
) : BaseCoroutinesUseCase<Banners>() {

    override suspend fun executeOnBackground(): Banners = launcherRepository.getBanners()
}