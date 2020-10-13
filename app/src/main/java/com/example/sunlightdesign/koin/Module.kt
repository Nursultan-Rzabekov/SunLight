package com.example.sunlightdesign.koin

import android.text.format.DateUtils
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.sunlightdesign.BuildConfig
import com.example.sunlightdesign.data.source.dataSource.local.ToDoDatabase
import com.example.sunlightdesign.usecase.usercase.SharedUseCase
import com.example.sunlightdesign.utils.HeaderInterceptor
import com.example.sunlightdesign.utils.SecureSharedPreferences
import com.example.sunlightdesign.utils.TokenAuthenticator
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val module = module {

    single {
        val preferences = "shared"
        val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        EncryptedSharedPreferences.create(
            preferences,
            masterKeyAlias,
            androidContext(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    single {
        SecureSharedPreferences(
            sharedPreferences = get()
        )
    }

    single {
        val client = OkHttpClient.Builder()
            .authenticator(TokenAuthenticator())
            .connectTimeout(DateUtils.MINUTE_IN_MILLIS, TimeUnit.MILLISECONDS)
            .writeTimeout(DateUtils.MINUTE_IN_MILLIS, TimeUnit.MILLISECONDS)
            .readTimeout(DateUtils.MINUTE_IN_MILLIS, TimeUnit.MILLISECONDS)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        client.addInterceptor(interceptor)
        client.addInterceptor(ChuckInterceptor(androidContext()))
        client.addInterceptor(HeaderInterceptor())
        client.build() as OkHttpClient
    }


    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(get())
            .build()
    }


    single {
        Room.databaseBuilder(
            androidContext(),
            ToDoDatabase::class.java,
            "Database.db"
        ).build()
    }

    single { get<ToDoDatabase>().taskDao() }

    factory {
        SharedUseCase(
            secureSharedPreferences = get()
        )
    }

}

