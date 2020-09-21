package com.example.sunlightdesign.koin

import android.text.format.DateUtils
import androidx.room.Room
import com.example.sunlightdesign.BaseApplication
import com.example.sunlightdesign.BaseApplication.Companion.context
import com.example.sunlightdesign.BuildConfig
import com.example.sunlightdesign.data.source.AuthDataSource
import com.example.sunlightdesign.data.source.AuthRepository
import com.example.sunlightdesign.data.source.local.AuthDao
import com.example.sunlightdesign.data.source.local.AuthLocalDataSource
import com.example.sunlightdesign.data.source.local.ToDoDatabase
import com.example.sunlightdesign.data.source.remote.auth.AuthRemoteDataSource
import com.example.sunlightdesign.data.source.remote.auth.AuthServices
import com.example.sunlightdesign.data.source.repositories.DefaultAuthRepository
import com.example.sunlightdesign.ui.launcher.auth.AuthActivity
import com.example.sunlightdesign.ui.launcher.auth.AuthViewModel
import com.example.sunlightdesign.usecase.usercase.authUse.GetLoginAuthUseCase
import com.example.sunlightdesign.utils.Prefs
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.readystatesoftware.chuck.ChuckInterceptor
import io.reactivex.schedulers.Schedulers.single
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.compat.ScopeCompat.viewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val module = module {

    single {
        val client = OkHttpClient.Builder()
        .connectTimeout(DateUtils.MINUTE_IN_MILLIS, TimeUnit.MILLISECONDS)
        .writeTimeout(DateUtils.MINUTE_IN_MILLIS, TimeUnit.MILLISECONDS)
        .readTimeout(DateUtils.MINUTE_IN_MILLIS, TimeUnit.MILLISECONDS)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        client.addInterceptor(interceptor)
        client.addInterceptor(ChuckInterceptor(BaseApplication.context))
        client.addInterceptor(HeaderInterceptor())
        client.build() as OkHttpClient
    }

    single {
        Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(get())
        .build()
    }

    single(named("authService")) {
        get<Retrofit>().create(AuthServices::class.java)
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            ToDoDatabase::class.java,
            "Auth.db")
            .build()
    }

    single { get<ToDoDatabase>().taskDao() }

    single { Prefs(androidContext()) }

    single<AuthDataSource>(named("RemoteDataSource")) {
        AuthRemoteDataSource(
            apiServices = get(named("authService"))
        )
    }

    single<AuthDataSource>(named("LocalDataSource")) {
        AuthLocalDataSource(
            tasksDao = get(),
            ioDispatcher = Dispatchers.IO)
    }

    single<AuthRepository> {
        DefaultAuthRepository(
            tasksLocalDataSource = get(named("LocalDataSource")),
            tasksRemoteDataSource = get(named("RemoteDataSource")),
            prefs = get(),
            ioDispatcher = Dispatchers.IO
        )
    }

    factory {
        GetLoginAuthUseCase(
            itemsRepository = get()
        )
    }

    viewModel {
        AuthViewModel(
            getItemsUseCase = get()
        )
    }
}


class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                //.addHeader("Authorization", "Bearer ${}")
                .build()
        )
    }
}
