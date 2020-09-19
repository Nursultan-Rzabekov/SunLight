package com.example.sunlightdesign.ui.launcher.company.di

import com.example.sunlightdesign.ui.launcher.company.CompanyFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CompanyFragmentBuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeCompanyFragment(): CompanyFragment
}