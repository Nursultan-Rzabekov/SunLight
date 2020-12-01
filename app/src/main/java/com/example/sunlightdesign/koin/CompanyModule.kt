package com.example.sunlightdesign.koin

import com.example.sunlightdesign.ui.launcher.company.CompanyViewModel
import com.example.sunlightdesign.usecase.usercase.mainUse.get.GetСompanyInfoUseCase
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