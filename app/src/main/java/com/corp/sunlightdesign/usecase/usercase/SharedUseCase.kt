package com.corp.sunlightdesign.usecase.usercase


import com.corp.sunlightdesign.utils.SecureSharedPreferences


class SharedUseCase constructor(
    private val secureSharedPreferences: SecureSharedPreferences
){
    fun getSharedPreference() : SecureSharedPreferences{
        return secureSharedPreferences
    }
}



