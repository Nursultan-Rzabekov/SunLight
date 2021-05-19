package com.corp.sunlightdesign.koin

import com.corp.sunlightdesign.data.source.AuthRepository
import com.corp.sunlightdesign.data.source.dataSource.AuthDataSource
import com.corp.sunlightdesign.data.source.dataSource.local.auth.AuthLocalDataSource
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.AuthRemoteDataSource
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.AuthServices
import com.corp.sunlightdesign.data.source.repositories.DefaultAuthRepository
import com.corp.sunlightdesign.ui.launcher.auth.AuthViewModel
import com.corp.sunlightdesign.usecase.usercase.authUse.GetLoginAuthUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val authModule = module {

    single(named("authService")) {
        get<Retrofit>().create(AuthServices::class.java)
    }

    single<AuthDataSource>(named("authRemoteDataSource")) {
        AuthRemoteDataSource(
            apiServices = get(named("authService"))
        )
    }

    single<AuthDataSource>(named("authLocalDataSource")) {
        AuthLocalDataSource(
            tasksDao = get(),
            ioDispatcher = Dispatchers.IO
        )
    }

    single<AuthRepository> {
        DefaultAuthRepository(
            tasksLocalDataSource = get(named("authLocalDataSource")),
            tasksRemoteDataSource = get(named("authRemoteDataSource")),
            prefs = get(),
            ioDispatcher = Dispatchers.IO
        )
    }

    factory {
        GetLoginAuthUseCase(
            itemsRepository = get()
        )
    }

    viewModel {
        AuthViewModel(
            sharedUseCase = get(),
            getItemsUseCase = get()
        )
    }
}

