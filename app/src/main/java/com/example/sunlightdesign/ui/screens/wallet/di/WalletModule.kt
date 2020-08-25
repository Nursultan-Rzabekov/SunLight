package com.example.sunlightdesign.ui.screens.wallet.di

import androidx.lifecycle.ViewModel
import com.example.sunlightdesign.di.ViewModelKey
import com.example.sunlightdesign.ui.screens.home.HomeViewModel
import com.example.sunlightdesign.ui.screens.wallet.WalletViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class WalletModule {

    @Binds
    @IntoMap
    @ViewModelKey(WalletViewModel::class)
    abstract fun bindViewModel(viewmodel: WalletViewModel): ViewModel
}
