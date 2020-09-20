package com.example.sunlightdesign.di

import com.example.sunlightdesign.data.source.*
import com.example.sunlightdesign.data.source.repositories.*
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppModuleBinds {

    @Singleton
    @Binds
    abstract fun bindRepositoryAuth(repo: DefaultAuthRepository): AuthRepository

    @Singleton
    @Binds
    abstract fun bindRepositoryMessenger(repo: DefaultMessengerRepository): MessengerRepository

    @Singleton
    @Binds
    abstract fun bindRepositoryAccount(repo: DefaultAccountRepository): AccountRepository

    @Singleton
    @Binds
    abstract fun bindRepositoryLauncher(repo: DefaultLauncherRepository): LauncherRepository

    @Singleton
    @Binds
    abstract fun bindRepositoryOrders(repo: DefaultOrdersRepository): OrdersRepository

    @Singleton
    @Binds
    abstract fun bindRepositoryWallet(repo: DefaultWalletRepository): WalletRepository

}
