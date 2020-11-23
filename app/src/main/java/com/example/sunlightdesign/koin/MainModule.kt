package com.example.sunlightdesign.koin

import com.example.sunlightdesign.data.source.LauncherRepository
import com.example.sunlightdesign.data.source.dataSource.remote.main.MainServices
import com.example.sunlightdesign.data.source.repositories.DefaultLauncherRepository
import com.example.sunlightdesign.ui.launcher.LauncherViewModel
import com.example.sunlightdesign.ui.screens.home.HomeViewModel
import com.example.sunlightdesign.ui.screens.home.structure.StructureViewModel
import com.example.sunlightdesign.usecase.usercase.mainUse.get.*
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