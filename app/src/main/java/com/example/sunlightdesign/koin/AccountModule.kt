package com.example.sunlightdesign.koin


import com.example.sunlightdesign.data.source.AccountRepository
import com.example.sunlightdesign.data.source.dataSource.AccountDataSource
import com.example.sunlightdesign.data.source.dataSource.remote.account.AccountRemoteDataSource
import com.example.sunlightdesign.data.source.dataSource.remote.account.AccountServices
import com.example.sunlightdesign.data.source.repositories.DefaultAccountRepository
import com.example.sunlightdesign.ui.screens.profile.ProfileViewModel
import com.example.sunlightdesign.usecase.usercase.accountUse.GetAccountCountriesUseCase
import com.example.sunlightdesign.usecase.usercase.accountUse.GetAccountUsersListUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit


val accountModule = module {

    single(named("accountService")) {
        get<Retrofit>().create(AccountServices::class.java)
    }

    single<AccountDataSource>(named("accountRemoteDataSource")) {
        AccountRemoteDataSource(
            apiServices = get(named("accountService"))
        )
    }

    single<AccountRepository> {
        DefaultAccountRepository(
            tasksRemoteDataSource = get(named("accountRemoteDataSource")),
            prefs = get(),
            ioDispatcher = Dispatchers.IO
        )
    }

    factory {
        GetAccountCountriesUseCase(
            itemsRepository = get()
        )
    }

    factory {
        GetAccountUsersListUseCase(
            itemsRepository = get()
        )
    }

    viewModel {
        ProfileViewModel(
            getAccountCountriesUseCase = get(),
            getAccountUsersListUseCase = get()
        )
    }
}

