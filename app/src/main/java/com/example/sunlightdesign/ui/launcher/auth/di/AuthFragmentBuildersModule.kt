package com.example.sunlightdesign.ui.launcher.auth.di

import com.example.sunlightdesign.ui.launcher.auth.login.LoginFragment
import com.example.sunlightdesign.ui.launcher.auth.register.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AuthFragmentBuildersModule {

    @ContributesAndroidInjector()
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector()
    abstract fun contributeRegisterFragmentStepOneFragment(): RegisterFragmentStepOne

    @ContributesAndroidInjector()
    abstract fun contributeRegisterFragmentStepTwoFragment(): RegisterFragmentStepTwo

    @ContributesAndroidInjector()
    abstract fun contributeRegisterFragmentStepThreeFragment(): RegisterFragmentStepThree

    @ContributesAndroidInjector()
    abstract fun contributeRegisterFragmentStepFourFragment(): RegisterFragmentStepFour

    @ContributesAndroidInjector()
    abstract fun contributeRegisterFragmentStepFiveFragment(): RegisterFragmentStepFive
}