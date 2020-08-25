package com.example.sunlightdesign.ui.screens.email.di

import com.example.sunlightdesign.ui.screens.email.EmailFragment
import com.example.sunlightdesign.ui.screens.home.HomeFragment
import com.example.sunlightdesign.ui.screens.list.ListFragment
import dagger.Subcomponent

@Subcomponent(modules = [EmailModule::class])
interface EmailComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): EmailComponent
    }

    fun inject(fragment: EmailFragment)


}
