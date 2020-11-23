package com.example.sunlightdesign.usecase.usercase.mainUse.get

import com.example.sunlightdesign.data.source.LauncherRepository
import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.Post
import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.SinglePost
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase

class GetPostByIdUseCase(
    private val launcherRepository: LauncherRepository
) : BaseCoroutinesUseCase<SinglePost?>() {

    private var model: PostByIdModel? = null

    fun setData(model: PostByIdModel) {
        this.model = model
    }

    override suspend fun executeOnBackground(): SinglePost? =
        this.model?.let{ launcherRepository.getPostById(it.id) }
}

data class PostByIdModel(
    val id: Int
)