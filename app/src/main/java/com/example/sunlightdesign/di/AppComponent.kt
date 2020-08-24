package com.example.sunlightdesign.di

import android.content.Context
import com.example.sunlightdesign.BaseApplication
import com.example.sunlightdesign.data.source.TasksRepository
import com.example.sunlightdesign.ui.screens.tasks.di.TasksComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Main component for the application.
 */
@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        AppModuleBinds::class,
        ViewModelBuilderModule::class,
        SubcomponentsModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

//    fun addEditTaskComponent(): AddEditTaskComponent.Factory
//    fun statisticsComponent(): StatisticsComponent.Factory
//    fun taskDetailComponent(): TaskDetailComponent.Factory
    fun tasksComponent(): TasksComponent.Factory

    val tasksRepository: TasksRepository
}

@Module(
    subcomponents = [
        TasksComponent::class
//        AddEditTaskComponent::class,
//        StatisticsComponent::class,
//        TaskDetailComponent::class
    ]
)
object SubcomponentsModule