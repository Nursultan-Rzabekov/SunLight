package com.example.sunlightdesign.di

import android.content.Context
import com.example.sunlightdesign.data.source.*
import com.example.sunlightdesign.ui.launcher.di.AuthComponent
import com.example.sunlightdesign.ui.screens.email.di.EmailComponent
import com.example.sunlightdesign.ui.screens.home.di.HomeComponent
import com.example.sunlightdesign.ui.screens.list.di.ListComponent
import com.example.sunlightdesign.ui.screens.profile.di.ProfileComponent
import com.example.sunlightdesign.ui.screens.wallet.di.WalletComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module
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
        ActivityBuildersModule::class,
        ViewModelBuilderModule::class,
        SubcomponentsModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    fun addWalletComponent(): WalletComponent.Factory
    fun addProfileComponent(): ProfileComponent.Factory
    fun addListComponent(): ListComponent.Factory
    fun addEmailComponent(): EmailComponent.Factory
    fun addHomeComponent(): HomeComponent.Factory
    fun addAuthComponent(): AuthComponent.Factory

    val authRepository: AuthRepository
    val walletRepository: WalletRepository
    val accountRepository: AccountRepository
    val messengerRepository: MessengerRepository
    val launcherRepository: LauncherRepository
    val ordersRepository: OrdersRepository
}

@Module(
    subcomponents = [
        HomeComponent::class,
        WalletComponent::class,
        ListComponent::class,
        ProfileComponent::class,
        EmailComponent::class
    ]
)
object SubcomponentsModule