package com.example.sunlightdesign.koin

import com.example.sunlightdesign.data.source.WalletRepository
import com.example.sunlightdesign.data.source.dataSource.remote.wallets.WalletsServices
import com.example.sunlightdesign.data.source.repositories.DefaultWalletRepository
import com.example.sunlightdesign.ui.screens.wallet.WalletViewModel
import com.example.sunlightdesign.usecase.usercase.walletUse.WalletCalculateInfoUseCase
import com.example.sunlightdesign.usecase.usercase.walletUse.WalletInfoUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val walletModule = module {

    single(named("walletService")) {
        get<Retrofit>().create(WalletsServices::class.java)
    }

    single<WalletRepository> {
        DefaultWalletRepository(
            walletsServices = get(named("walletService"))
        )
    }

    factory {
        WalletInfoUseCase(
            walletRepository = get()
        )
    }

    factory {
        WalletCalculateInfoUseCase(
            repository = get()
        )
    }

    viewModel {
        WalletViewModel(
            walletInfoUseCase = get(),
            walletCalculateInfoUseCase = get()
        )
    }
}