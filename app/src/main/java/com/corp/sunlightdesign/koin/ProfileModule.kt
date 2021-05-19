package com.corp.sunlightdesign.koin

import com.corp.sunlightdesign.data.source.ProfileRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.profile.ProfileServices
import com.corp.sunlightdesign.data.source.repositories.DefaultProfileRepository
import com.corp.sunlightdesign.ui.screens.profile.verification.UserVerificationViewModel
import com.corp.sunlightdesign.usecase.usercase.profileUse.get.GetVerificationInfoUseCase
import com.corp.sunlightdesign.usecase.usercase.profileUse.get.GetVerifyHelperUseCase
import com.corp.sunlightdesign.usecase.usercase.profileUse.get.ProfileInfoUseCase
import com.corp.sunlightdesign.usecase.usercase.profileUse.post.ProfileChangeAvatarUseCase
import com.corp.sunlightdesign.usecase.usercase.profileUse.post.ProfileChangePasswordUseCase
import com.corp.sunlightdesign.usecase.usercase.profileUse.post.VerifyUserUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val profileModule = module {

    single(named("profileService")) {
        get<Retrofit>().create(ProfileServices::class.java)
    }

    single<ProfileRepository> {
        DefaultProfileRepository(
            services = get(named("profileService"))
        )
    }

    factory {
        ProfileInfoUseCase(
            profileRepository = get()
        )
    }

    factory {
        ProfileChangePasswordUseCase(
            repository = get()
        )
    }

    factory {
        ProfileChangeAvatarUseCase(
            profileRepository = get()
        )
    }

    factory {
        GetVerifyHelperUseCase(
            repository = get()
        )
    }

    factory {
        VerifyUserUseCase(
            repository = get()
        )
    }

    factory {
        GetVerificationInfoUseCase(
            repository = get()
        )
    }

    viewModel {
        UserVerificationViewModel(
            getVerifyHelperUseCase = get(),
            verifyUserUseCase = get(),
            getVerificationInfoUseCase = get()
        )
    }
}