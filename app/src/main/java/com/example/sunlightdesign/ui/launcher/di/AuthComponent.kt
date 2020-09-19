package com.example.sunlightdesign.ui.launcher.di

import com.example.sunlightdesign.ui.launcher.LauncherActivity
import dagger.Subcomponent

@Subcomponent(modules = [AuthModule::class])
interface AuthComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): AuthComponent
    }

    fun inject(fragment: LauncherActivity)

}
