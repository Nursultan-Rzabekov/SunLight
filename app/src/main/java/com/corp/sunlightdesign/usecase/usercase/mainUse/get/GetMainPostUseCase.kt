package com.corp.sunlightdesign.usecase.usercase.mainUse.get

import com.corp.sunlightdesign.data.source.LauncherRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.main.entity.Posts
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase

class GetMainPostUseCase constructor(
    private val launcherRepository: LauncherRepository
) : BaseCoroutinesUseCase<Posts>() {
    override suspend fun executeOnBackground(): Posts = launcherRepository.getPosts()
}