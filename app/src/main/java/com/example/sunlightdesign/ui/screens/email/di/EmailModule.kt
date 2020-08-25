package com.example.sunlightdesign.ui.screens.email.di

import androidx.lifecycle.ViewModel
import com.example.sunlightdesign.di.ViewModelKey
import com.example.sunlightdesign.ui.screens.email.EmailViewModel
import com.example.sunlightdesign.ui.screens.home.HomeViewModel
import com.example.sunlightdesign.ui.screens.list.ListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class EmailModule {

    @Binds
    @IntoMap
    @ViewModelKey(EmailViewModel::class)
    abstract fun bindViewModel(viewmodel: EmailViewModel): ViewModel
}
