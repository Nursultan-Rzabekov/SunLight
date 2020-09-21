package com.example.sunlightdesign

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.example.sunlightdesign.koin.module
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

open class BaseApplication : MultiDexApplication() {

    companion object{
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(DebugTree())

        context = applicationContext

        startKoin {
            // use Koin logger
            printLogger()
            androidContext(this@BaseApplication)
            // declare modules
            modules(module)
        }


    }
}
