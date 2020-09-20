package com.example.sunlightdesign.utils

import android.content.Context
import android.content.SharedPreferences


class Prefs(context: Context) {
    private val APP_PREFERENCES: String = "default"
    private val prefs: SharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    private val LANG = "LANG"
    var lang: String?
        get() = prefs.getString(LANG, null)
        set(value) = prefs.edit().putString(LANG, value).apply()

    private val BEARER_TOKEN = "is_bearer_token"
    var bearerToken: String?
        get() = prefs.getString(BEARER_TOKEN, null)
        set(value) = prefs.edit().putString(BEARER_TOKEN, value).apply()

    private val PIN_WRONG_COUNT = "pin_wrong_count"
    var pinWrongCount: Int
        get() = prefs.getInt(PIN_WRONG_COUNT, 0)
        set(count) = prefs.edit().putInt(PIN_WRONG_COUNT, count).apply()


}