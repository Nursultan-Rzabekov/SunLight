package com.corp.sunlightdesign.koin

import com.corp.sunlightdesign.ui.launcher.company.CompanyViewModel
import com.corp.sunlightdesign.usecase.usercase.mainUse.get.GetСompanyInfoUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val companyModule = module {

    factory {
        GetСompanyInfoUseCase(
            launcherRepository = get()
        )
    }

    viewModel {
        CompanyViewModel(
            getCompanyInfoUseCase = get()
        )
    }

}