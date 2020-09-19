package com.example.sunlightdesign.ui.auth.company.di

import com.example.sunlightdesign.ui.auth.company.CompanyFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CompanyFragmentBuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeCompanyFragment(): CompanyFragment
}