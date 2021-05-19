package com.corp.sunlightdesign.usecase.usercase.mainUse.get

import com.corp.sunlightdesign.data.source.LauncherRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.main.entity.Posts
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase

class GetPostsByCategoryId constructor(
    private val launcherRepository: LauncherRepository
) : BaseCoroutinesUseCase<Posts>() {

    private var id: Int = 0

    fun setItems(id: Int) {
        this.id = id
    }

    override suspend fun executeOnBackground() = launcherRepository.getByCategoryId(id)
}