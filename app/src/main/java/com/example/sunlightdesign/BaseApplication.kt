package com.example.sunlightdesign

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDexApplication
import com.example.sunlightdesign.koin.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

class BaseApplication : MultiDexApplication(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(DebugTree())
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)

        startKoin {
            // use Koin logger
            printLogger()
            androidContext(this@BaseApplication)
            // declare modules
            modules(
                module,
                mainModule,
                authModule,
                accountModule,
                announcementsModule,
                ordersModule,
                profileModule,
                walletModule,
                companyModule
            )
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {

    }
}
