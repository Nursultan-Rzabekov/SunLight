package com.example.sunlightdesign.di



import com.example.sunlightdesign.ui.launcher.auth.AuthActivity
import com.example.sunlightdesign.ui.launcher.auth.di.AuthFragmentBuildersModule
import com.example.sunlightdesign.ui.launcher.auth.di.AuthModule
import com.example.sunlightdesign.ui.launcher.auth.di.AuthScope
import com.example.sunlightdesign.ui.launcher.company.CompanyActivity
import com.example.sunlightdesign.ui.launcher.company.di.CompanyFragmentBuildersModule
import com.example.sunlightdesign.ui.launcher.company.di.CompanyModule
import com.example.sunlightdesign.ui.launcher.company.di.CompanyScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @CompanyScope
    @ContributesAndroidInjector(
        modules = [CompanyFragmentBuildersModule::class, CompanyModule::class]
    )
    abstract fun contributeCompanyActivity(): CompanyActivity

    @AuthScope
    @ContributesAndroidInjector(
        modules = [AuthFragmentBuildersModule::class, AuthModule::class]
    )
    abstract fun contributeAuthActivity(): AuthActivity

}