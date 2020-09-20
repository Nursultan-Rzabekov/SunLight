package com.example.sunlightdesign

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.example.sunlightdesign.di.AppComponent
import com.example.sunlightdesign.di.DaggerAppComponent
import timber.log.Timber
import timber.log.Timber.DebugTree

open class BaseApplication : MultiDexApplication() {

    companion object{
        lateinit var context: Context
    }

    // Instance of the AppComponent that will be used by all the Activities in the project
    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        return DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(DebugTree())

        context = applicationContext


    }
}
