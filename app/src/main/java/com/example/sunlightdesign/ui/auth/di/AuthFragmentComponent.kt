package com.example.sunlightdesign.ui.auth.di

import com.example.sunlightdesign.ui.auth.AuthActivity
import com.example.sunlightdesign.ui.auth.AuthFragment
import com.example.sunlightdesign.ui.screens.email.EmailFragment
import com.example.sunlightdesign.ui.screens.home.HomeFragment
import com.example.sunlightdesign.ui.screens.list.ListFragment
import dagger.Subcomponent

@Subcomponent(modules = [AuthModule::class])
interface AuthFragmentComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): AuthFragmentComponent
    }

    fun inject(fragment: AuthFragment)

}
