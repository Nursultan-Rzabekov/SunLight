package com.example.sunlightdesign.ui.launcher.di

import com.example.sunlightdesign.ui.launcher.LauncherFragment
import dagger.Subcomponent

@Subcomponent(modules = [AuthModule::class])
interface AuthFragmentComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): AuthFragmentComponent
    }

    fun inject(fragment: LauncherFragment)

}
