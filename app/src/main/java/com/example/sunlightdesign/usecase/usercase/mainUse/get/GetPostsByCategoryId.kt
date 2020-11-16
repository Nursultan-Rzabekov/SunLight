package com.example.sunlightdesign.usecase.usercase.mainUse.get

import com.example.sunlightdesign.data.source.LauncherRepository
import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.Posts
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase

class GetPostsByCategoryId constructor(
    private val launcherRepository: LauncherRepository
) : BaseCoroutinesUseCase<Posts>() {

    private var id: Int = 0

    fun setItems(id: Int) {
        this.id = id
    }

    override suspend fun executeOnBackground() = launcherRepository.getByCategoryId(id)
}