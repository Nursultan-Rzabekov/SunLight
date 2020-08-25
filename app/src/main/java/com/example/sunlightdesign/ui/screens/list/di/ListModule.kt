package com.example.sunlightdesign.ui.screens.list.di

import androidx.lifecycle.ViewModel
import com.example.sunlightdesign.di.ViewModelKey
import com.example.sunlightdesign.ui.screens.home.HomeViewModel
import com.example.sunlightdesign.ui.screens.list.ListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ListModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel::class)
    abstract fun bindViewModel(viewmodel: ListViewModel): ViewModel
}
