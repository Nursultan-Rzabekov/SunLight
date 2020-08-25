package com.example.sunlightdesign.ui.screens.profile.di

import com.example.sunlightdesign.ui.screens.home.HomeFragment
import com.example.sunlightdesign.ui.screens.list.ListFragment
import com.example.sunlightdesign.ui.screens.profile.ProfileFragment
import com.example.sunlightdesign.ui.screens.profile.ProfileViewModel
import dagger.Subcomponent

@Subcomponent(modules = [ProfileModule::class])
interface ProfileComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ProfileComponent
    }

    fun inject(fragment: ProfileFragment)


}
