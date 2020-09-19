package com.example.sunlightdesign.ui.launcher.company.di

import com.example.sunlightdesign.ui.launcher.company.parts.AboutCompanyFragment
import com.example.sunlightdesign.ui.launcher.company.parts.ContactsCompanyFragment
import com.example.sunlightdesign.ui.launcher.company.parts.MarketPlanFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CompanyFragmentBuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeAboutCompanyFragment(): AboutCompanyFragment

    @ContributesAndroidInjector()
    abstract fun contributeMarketPlanFragment(): MarketPlanFragment

    @ContributesAndroidInjector
    abstract fun contributeContactsCompanyFragment(): ContactsCompanyFragment
}