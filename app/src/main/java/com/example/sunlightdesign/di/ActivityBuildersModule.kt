package com.example.sunlightdesign.di



import com.example.sunlightdesign.ui.auth.company.CompanyActivity
import com.example.sunlightdesign.ui.auth.company.di.CompanyFragmentBuildersModule
import com.example.sunlightdesign.ui.auth.company.di.CompanyModule
import com.example.sunlightdesign.ui.auth.company.di.CompanyScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @CompanyScope
    @ContributesAndroidInjector(
        modules = [CompanyFragmentBuildersModule::class, CompanyModule::class]
    )
    abstract fun contributeCompanyActivity(): CompanyActivity

}