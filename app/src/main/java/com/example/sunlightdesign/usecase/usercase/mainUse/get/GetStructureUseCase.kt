package com.example.sunlightdesign.usecase.usercase.mainUse.get

import com.example.sunlightdesign.data.source.LauncherRepository
import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.StructureInfo
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase

class GetStructureUseCase(
    private val launcherRepository: LauncherRepository
): BaseCoroutinesUseCase<StructureInfo>() {

    override suspend fun executeOnBackground(): StructureInfo =
        launcherRepository.getStructureInfo()
}