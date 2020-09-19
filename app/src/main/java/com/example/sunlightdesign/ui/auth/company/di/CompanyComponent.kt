package com.example.sunlightdesign.ui.auth.company.di

import com.example.sunlightdesign.ui.auth.company.CompanyActivity
import dagger.Subcomponent

@Subcomponent(modules = [CompanyModule::class])
interface CompanyComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): CompanyComponent
    }

    fun inject(fragment: CompanyActivity)

}
