package com.example.sunlightdesign.koin

import com.example.sunlightdesign.data.source.AuthRepository
import com.example.sunlightdesign.data.source.dataSource.AuthDataSource
import com.example.sunlightdesign.data.source.dataSource.local.auth.AuthLocalDataSource
import com.example.sunlightdesign.data.source.dataSource.remote.auth.AuthRemoteDataSource
import com.example.sunlightdesign.data.source.dataSource.remote.auth.AuthServices
import com.example.sunlightdesign.data.source.repositories.DefaultAuthRepository
import com.example.sunlightdesign.ui.launcher.LauncherViewModel
import com.example.sunlightdesign.ui.launcher.auth.AuthViewModel
import com.example.sunlightdesign.usecase.usercase.authUse.GetLoginAuthUseCase
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
            getItemsUseCase = get()
        )
    }
}

