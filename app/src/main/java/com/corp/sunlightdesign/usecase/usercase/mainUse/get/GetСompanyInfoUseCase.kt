package com.corp.sunlightdesign.usecase.usercase.mainUse.get

import com.corp.sunlightdesign.data.source.LauncherRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.main.entity.CompanyInfo
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase

class GetСompanyInfoUseCase constructor(
    private val launcherRepository: LauncherRepository
) : BaseCoroutinesUseCase<CompanyInfo>() {

    private var url: String = "contacts"
    fun setData(url:String){
        this.url = url
    }

    override suspend fun executeOnBackground() = launcherRepository.getCompanyInfo(url)
}