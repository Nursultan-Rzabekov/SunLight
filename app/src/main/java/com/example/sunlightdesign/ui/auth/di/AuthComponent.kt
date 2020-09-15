package com.example.sunlightdesign.ui.auth.di

import com.example.sunlightdesign.ui.auth.AuthActivity
import com.example.sunlightdesign.ui.screens.email.EmailFragment
import com.example.sunlightdesign.ui.screens.home.HomeFragment
import com.example.sunlightdesign.ui.screens.list.ListFragment
import dagger.Subcomponent

@Subcomponent(modules = [AuthModule::class])
interface AuthComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): AuthComponent
    }

    fun inject(fragment: AuthActivity)

}
