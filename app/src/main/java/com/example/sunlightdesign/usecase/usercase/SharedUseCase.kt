package com.example.sunlightdesign.usecase.usercase


import com.example.sunlightdesign.utils.SecureSharedPreferences


class SharedUseCase constructor(
    private val secureSharedPreferences: SecureSharedPreferences
){
    fun getSharedPreference() : SecureSharedPreferences{
        return secureSharedPreferences
    }
}

