package com.example.sunlightdesign.usecase.usercase.mainUse.get

import com.example.sunlightdesign.data.source.LauncherRepository
import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.Banners
import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.CompanyInfo
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase

class GetСompanyInfoUseCase constructor(
    private val launcherRepository: LauncherRepository
) : BaseCoroutinesUseCase<CompanyInfo>() {

    private var url: String = "contacts"
    fun setData(url:String){
        this.url = url
    }

    override suspend fun executeOnBackground() = launcherRepository.getCompanyInfo(url)
}