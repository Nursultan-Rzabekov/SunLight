package com.example.sunlightdesign.ui.screens.wallet.di

import com.example.sunlightdesign.ui.screens.home.HomeFragment
import com.example.sunlightdesign.ui.screens.list.ListFragment
import com.example.sunlightdesign.ui.screens.wallet.WalletFragment
import dagger.Subcomponent

@Subcomponent(modules = [WalletModule::class])
interface WalletComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): WalletComponent
    }

    fun inject(fragment: WalletFragment)


}
