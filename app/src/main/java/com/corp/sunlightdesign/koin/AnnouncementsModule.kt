package com.corp.sunlightdesign.koin

import com.corp.sunlightdesign.data.source.MessengerRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.email.AnnouncementsServices
import com.corp.sunlightdesign.data.source.repositories.DefaultMessengerRepository
import com.corp.sunlightdesign.ui.screens.email.EmailViewModel
import com.corp.sunlightdesign.usecase.usercase.emailUse.DeleteAnnouncementUseCase
import com.corp.sunlightdesign.usecase.usercase.emailUse.get.GetAnnouncementsUseCase
import com.corp.sunlightdesign.usecase.usercase.emailUse.get.ShowAnnouncementsDetailsUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val announcementsModule = module {

    single(named("announcementsService")) {
        get<Retrofit>().create(AnnouncementsServices::class.java)
    }

    single<MessengerRepository> {
        DefaultMessengerRepository(
            announcementsServices = get(named("announcementsService"))
        )
    }

    factory {
        GetAnnouncementsUseCase(
            itemsRepository = get()
        )
    }

    factory {
        ShowAnnouncementsDetailsUseCase(
            itemsRepository = get()
        )
    }


    factory {
        DeleteAnnouncementUseCase(
            itemsRepository = get()
        )
    }

    viewModel {
        EmailViewModel(
            getAnnouncementsUseCase = get(),
            showAnnouncementsDetailsUseCase = get(),
            deleteAnnouncementUseCase = get()
        )
    }
}