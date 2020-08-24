package com.example.sunlightdesign.di

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object NetworkModule {
//    private const val BASE_URL = "http://xyz/appname/"
//
//    @Provides
//    @Singleton
//    fun provideOkHttp(): OkHttpClient.Builder {
//        return Builder()
//    }
//
//    @Provides
//    @Singleton
//    fun provideRequestHeaders(): RequestHeaders {
//        return RequestHeaders(AccessToken(), "en", "application/json")
//    }
//
//    @Provides
//    @Singleton
//    fun providesRequestInterceptor(requestHeaders: RequestHeaders): RequestInterceptor {
//        return RequestInterceptor(requestHeaders)
//    }
//
//    @Provides
//    @Singleton
//    fun provideRetrofit(
//        httpClient: OkHttpClient.Builder,
//        requestInterceptor: RequestInterceptor?
//    ): Retrofit {
//        //add logger
//        val logging: HttpLoggingInterceptor =
//            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//        httpClient.addInterceptor(logging)
//        httpClient.addInterceptor(requestInterceptor)
//
//        //add retro builder
//        val retroBuilder: Retrofit.Builder = Builder()
//            .baseUrl(BASE_URL)
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create())
//        retroBuilder.client(httpClient.build())
//
//        //create retrofit - only this instance would be used in the entire application
//        return retroBuilder.build()
//    }
//
//    // API Services
//    @Provides
//    @Singleton
//    fun provideUserServices(
//        retrofit: Retrofit
//    ): UserServices {
//        return retrofit.create(UserServices::class.java)
//    }
//
//    @Provides
//    @Singleton
//    fun provideAccountServices(
//        retrofit: Retrofit
//    ): AccountsServices {
//        return retrofit.create(AccountsServices::class.java)
//    }
//
//
//    @Provides
//    @Singleton
//    fun provideSystemServices(
//        retrofit: Retrofit
//    ): SystemServices {
//        return retrofit.create(SystemServices::class.java)
//    }
}