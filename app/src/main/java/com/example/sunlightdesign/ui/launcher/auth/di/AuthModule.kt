package com.example.sunlightdesign.ui.launcher.auth.di

import androidx.lifecycle.ViewModel
import com.example.sunlightdesign.di.ViewModelKey
import com.example.sunlightdesign.ui.launcher.auth.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AuthModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindViewModel(viewModel: AuthViewModel): ViewModel
}
