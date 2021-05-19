package com.corp.sunlightdesign.koin

import com.corp.sunlightdesign.data.source.WalletRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.wallets.WalletsServices
import com.corp.sunlightdesign.data.source.repositories.DefaultWalletRepository
import com.corp.sunlightdesign.ui.screens.wallet.WalletViewModel
import com.corp.sunlightdesign.usecase.usercase.walletUse.get.WalletCalculateInfoUseCase
import com.corp.sunlightdesign.usecase.usercase.walletUse.get.WalletGetOfficesUseCase
import com.corp.sunlightdesign.usecase.usercase.walletUse.get.WalletInfoUseCase
import com.corp.sunlightdesign.usecase.usercase.walletUse.post.WalletStoreWithdrawalUseCase
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

    factory {
        WalletGetOfficesUseCase(
            walletRepository = get()
        )
    }

    factory {
        WalletStoreWithdrawalUseCase(
            walletRepository = get()
        )
    }

    viewModel {
        WalletViewModel(
            walletInfoUseCase = get(),
            walletCalculateInfoUseCase = get(),
            walletGetOfficesUseCase = get(),
            walletStoreWithdrawalUseCase = get()
        )
    }
}