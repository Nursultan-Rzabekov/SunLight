package com.example.sunlightdesign.utils

import android.content.SharedPreferences

class SecureSharedPreferences(
    val sharedPreferences: SharedPreferences
) {

    private val LANG = "LANG"
    var lang: String?
        get() = sharedPreferences.getString(LANG, null)
        set(value) = sharedPreferences.edit().putString(LANG, value).apply()

    private val BEARER_TOKEN = "is_bearer_token"
    var bearerToken: String?
        get() = sharedPreferences.getString(BEARER_TOKEN, null)
        set(value) = sharedPreferences.edit().putString(BEARER_TOKEN, value).apply()

    private val PIN_WRONG_COUNT = "pin_wrong_count"
    var pinWrongCount: Int
        get() = sharedPreferences.getInt(PIN_WRONG_COUNT, 0)
        set(count) = sharedPreferences.edit().putInt(PIN_WRONG_COUNT, count).apply()


}


