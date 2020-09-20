package com.example.sunlightdesign

import android.content.Context
import androidx.multidex.MultiDexApplication
import androidx.room.Room
import com.example.sunlightdesign.data.source.local.ToDoDatabase
import com.example.sunlightdesign.di.AppComponent
import com.example.sunlightdesign.di.DaggerAppComponent
import com.example.sunlightdesign.utils.Prefs
import timber.log.Timber
import timber.log.Timber.DebugTree

open class BaseApplication : MultiDexApplication() {

    companion object{
        lateinit var context: Context
        var db: ToDoDatabase? = null
        var prefs: Prefs? = null
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
        prefs = Prefs(applicationContext)
        db = Room.databaseBuilder(applicationContext, ToDoDatabase::class.java, "roomDatabase").build()
    }
}
