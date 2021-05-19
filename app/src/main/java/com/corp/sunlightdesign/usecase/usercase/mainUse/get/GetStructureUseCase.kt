package com.corp.sunlightdesign.usecase.usercase.mainUse.get

import com.corp.sunlightdesign.data.source.LauncherRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.main.entity.StructureInfo
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase

class GetStructureUseCase(
    private val launcherRepository: LauncherRepository
): BaseCoroutinesUseCase<StructureInfo>() {

    override suspend fun executeOnBackground(): StructureInfo =
        launcherRepository.getStructureInfo()
}