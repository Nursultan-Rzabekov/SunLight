package com.example.sunlightdesign.ui.screens.tasks.di

import com.example.sunlightdesign.ui.screens.tasks.TasksFragment
import dagger.Subcomponent

@Subcomponent(modules = [TasksModule::class])
interface TasksComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): TasksComponent
    }

    fun inject(fragment: TasksFragment)
}
