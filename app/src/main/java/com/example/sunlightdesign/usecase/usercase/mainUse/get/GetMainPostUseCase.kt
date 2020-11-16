package com.example.sunlightdesign.usecase.usercase.mainUse.get

import com.example.sunlightdesign.data.source.LauncherRepository
import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.Posts
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase

class GetMainPostUseCase constructor(
    private val launcherRepository: LauncherRepository
) : BaseCoroutinesUseCase<Posts>() {
    override suspend fun executeOnBackground(): Posts = launcherRepository.getPosts()
}