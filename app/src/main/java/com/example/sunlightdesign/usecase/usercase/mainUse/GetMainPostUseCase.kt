package com.example.sunlightdesign.usecase.usercase.mainUse

import com.example.sunlightdesign.data.source.LauncherRepository
import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.Post
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase

class GetMainPostUseCase constructor(
    private val launcherRepository: LauncherRepository
) : BaseCoroutinesUseCase<Post>() {
    override suspend fun executeOnBackground(): Post = launcherRepository.getPosts()
}