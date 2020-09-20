package com.example.sunlightdesign.ui.launcher.di

import com.example.sunlightdesign.ui.launcher.LauncherFragment
import dagger.Subcomponent

@Subcomponent(modules = [LauncherModule::class])
interface LauncherComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): LauncherComponent
    }

    fun inject(fragment: LauncherFragment)

}
