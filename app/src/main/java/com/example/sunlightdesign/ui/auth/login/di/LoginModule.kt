package com.example.sunlightdesign.ui.auth.login.di

import androidx.lifecycle.ViewModel
import com.example.sunlightdesign.di.ViewModelKey
import com.example.sunlightdesign.ui.auth.AuthViewModel
import com.example.sunlightdesign.ui.auth.company.CompanyViewModel
import com.example.sunlightdesign.ui.auth.login.LoginViewModel
import com.example.sunlightdesign.ui.screens.email.EmailViewModel
import com.example.sunlightdesign.ui.screens.home.HomeViewModel
import com.example.sunlightdesign.ui.screens.list.ListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class LoginModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindViewModel(viewmodel: LoginViewModel): ViewModel
}
