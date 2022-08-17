package com.corp.sunlightdesign.koin

import com.corp.sunlightdesign.data.source.LauncherRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.main.MainServices
import com.corp.sunlightdesign.data.source.repositories.DefaultLauncherRepository
import com.corp.sunlightdesign.ui.launcher.LauncherViewModel
import com.corp.sunlightdesign.ui.screens.home.HomeViewModel
import com.corp.sunlightdesign.ui.screens.home.structure.StructureViewModel
import com.corp.sunlightdesign.usecase.usercase.authUse.SetFirebaseTokenUseCase
import com.corp.sunlightdesign.usecase.usercase.mainUse.get.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val mainModule = module {

    single(named("mainServices")) {
        get<Retrofit>().create(MainServices::class.java)
    }

    single<LauncherRepository> {
        DefaultLauncherRepository(
            mainServices = get(named("mainServices"))
        )
    }

    factory {
        GetMainBannersUseCase(
            launcherRepository = get()
        )
    }

    factory {
        GetMainCategoriesUseCase(
            launcherRepository = get()
        )
    }

    factory {
        GetMainPostUseCase(
            launcherRepository = get()
        )
    }

    factory {
        GetPostsByCategoryId(
            launcherRepository = get()
        )
    }

    factory {
        GetStructureUseCase(
            launcherRepository = get()
        )
    }

    factory {
        GetPostByIdUseCase(
            launcherRepository = get()
        )
    }

    factory {
        SetFirebaseTokenUseCase(
            repository = get()
        )
    }

    viewModel {
        LauncherViewModel(
            sharedUseCase = get(),
            getMainPostUseCase = get(),
            getMainCategoriesUseCase = get(),
            getMainBannersUseCase = get(),
            getPostsByCategoryId = get(),
            getPostByIdUseCase = get()
        )
    }

    viewModel {
        HomeViewModel(
            sharedUseCase = get(),
            getMainPostUseCase = get(),
            getMainCategoriesUseCase = get(),
            getMainBannersUseCase = get(),
            getPostsByCategoryId = get()
        )
    }

    viewModel {
        StructureViewModel(
            getStructureUseCase = get()
        )
    }
}