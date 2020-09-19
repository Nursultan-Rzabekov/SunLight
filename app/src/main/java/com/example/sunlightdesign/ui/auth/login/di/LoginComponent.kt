package com.example.sunlightdesign.ui.auth.login.di

import com.example.sunlightdesign.ui.auth.login.LoginFragment
import dagger.Subcomponent

@Subcomponent(modules = [LoginModule::class])
interface LoginComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginComponent
    }

    fun inject(fragment: LoginFragment)

}
