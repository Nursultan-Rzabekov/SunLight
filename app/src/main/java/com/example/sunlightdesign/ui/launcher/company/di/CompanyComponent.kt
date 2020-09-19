package com.example.sunlightdesign.ui.launcher.company.di

import com.example.sunlightdesign.ui.launcher.company.CompanyActivity
import dagger.Subcomponent

@Subcomponent(modules = [CompanyModule::class])
interface CompanyComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): CompanyComponent
    }

    fun inject(fragment: CompanyActivity)

}
