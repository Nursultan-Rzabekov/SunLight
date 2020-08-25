package com.example.sunlightdesign.ui.screens.list.di

import com.example.sunlightdesign.ui.screens.home.HomeFragment
import com.example.sunlightdesign.ui.screens.list.ListFragment
import dagger.Subcomponent

@Subcomponent(modules = [ListModule::class])
interface ListComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ListComponent
    }

    fun inject(fragment: ListFragment)


}
