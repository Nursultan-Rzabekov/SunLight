package com.example.sunlightdesign

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.example.sunlightdesign.koin.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

class BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(DebugTree())

        startKoin {
            // use Koin logger
            printLogger()
            androidContext(this@BaseApplication)
            // declare modules
            modules(module, authModule, accountModule, announcementsModule, ordersModule)
        }
    }
}
