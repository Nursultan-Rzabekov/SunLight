package com.example.sunlightdesign.ui.screens.tasks.di

import com.example.sunlightdesign.ui.screens.tasks.HomeFragment
import dagger.Subcomponent

@Subcomponent(modules = [HomeModule::class])
interface HomeComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): HomeComponent
    }

    fun inject(fragment: HomeFragment)


}
