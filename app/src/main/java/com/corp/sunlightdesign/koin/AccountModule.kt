package com.corp.sunlightdesign.koin


import com.corp.sunlightdesign.data.source.AccountRepository
import com.corp.sunlightdesign.data.source.dataSource.AccountDataSource
import com.corp.sunlightdesign.data.source.dataSource.remote.account.AccountRemoteDataSource
import com.corp.sunlightdesign.data.source.dataSource.remote.account.AccountServices
import com.corp.sunlightdesign.data.source.repositories.DefaultAccountRepository
import com.corp.sunlightdesign.ui.screens.profile.ProfileViewModel
import com.corp.sunlightdesign.usecase.usercase.accountUse.get.AccountCountriesUseCase
import com.corp.sunlightdesign.usecase.usercase.accountUse.get.AccountOfficesListUseCase
import com.corp.sunlightdesign.usecase.usercase.accountUse.get.AccountPackagesListUseCase
import com.corp.sunlightdesign.usecase.usercase.accountUse.get.AccountUsersListUseCase
import com.corp.sunlightdesign.usecase.usercase.accountUse.post.AccountAddPartnerUseCase
import com.corp.sunlightdesign.usecase.usercase.accountUse.post.AccountCreateOrderUseCase
import com.corp.sunlightdesign.usecase.usercase.accountUse.post.AccountSetPackagesUseCase
import com.corp.sunlightdesign.usecase.usercase.profileUse.get.GetInvitesUseCase
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
        AccountCountriesUseCase(
            itemsRepository = get()
        )
    }

    factory {
        AccountUsersListUseCase(
            itemsRepository = get()
        )
    }

    factory {
        AccountAddPartnerUseCase(
            itemsRepository = get()
        )
    }


    factory {
        AccountCreateOrderUseCase(
            itemsRepository = get()
        )
    }


    factory {
        AccountSetPackagesUseCase(
            itemsRepository = get()
        )
    }

    factory {
        AccountOfficesListUseCase(
            itemsRepository = get()
        )
    }

    factory {
        AccountPackagesListUseCase(
            itemsRepository = get()
        )
    }

    factory {
        GetInvitesUseCase(
            profileRepository = get()
        )
    }

    viewModel {
        ProfileViewModel(
            sharedUseCase = get(),
            accountCountriesUseCase = get(),
            accountUsersListUseCase = get(),
            accountPackagesListUseCase = get(),
            accountOfficesListUseCase = get(),
            accountSetPackagesUseCase = get(),
            accountAddPartnerUseCase = get(),
            accountCreateOrderUseCase = get(),
            profileInfoUseCase = get(),
            profileChangePasswordUseCase = get(),
            profileChangeAvatarUseCase = get(),
            profileGetInvitesUseCase = get(),
            calculateDeliveryUseCase = get()
        )
    }
}

