package com.example.sunlightdesign.koin

import com.example.sunlightdesign.data.source.ProfileRepository
import com.example.sunlightdesign.data.source.dataSource.remote.profile.ProfileServices
import com.example.sunlightdesign.data.source.repositories.DefaultProfileRepository
import com.example.sunlightdesign.usecase.usercase.profileUse.get.ProfileInfoUseCase
import com.example.sunlightdesign.usecase.usercase.profileUse.post.ProfileChangePasswordUseCase
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
}