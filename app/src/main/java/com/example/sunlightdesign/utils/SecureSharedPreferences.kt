package com.example.sunlightdesign.utils

import android.content.SharedPreferences

class SecureSharedPreferences(val sharedPreferences: SharedPreferences) {

    private val LANG = "LANG"
    var lang: String?
        get() = sharedPreferences.getString(LANG, null)
        set(value) = sharedPreferences.edit().putString(LANG, value).apply()

    private val BEARER_TOKEN = "is_bearer_token"
    var bearerToken: String?
        get() = sharedPreferences.getString(BEARER_TOKEN, null)
        set(value) = sharedPreferences.edit().putString(BEARER_TOKEN, value).apply()


    private val USER_ID = "user_id"
    var userId:String?
        get() = sharedPreferences.getString(USER_ID,null)
        set(value) = sharedPreferences.edit().putString(USER_ID,value).apply()

    private val PIN_WRONG_COUNT = "pin_wrong_count"
    var pinWrongCount: Int
        get() = sharedPreferences.getInt(PIN_WRONG_COUNT, 0)
        set(count) = sharedPreferences.edit().putInt(PIN_WRONG_COUNT, count).apply()

    private val PHONE_NUMBER = "phone_number_last"
    var phoneNumber: String?
        get() = sharedPreferences.getString(PHONE_NUMBER,null)
        set(value) = sharedPreferences.edit().putString(PHONE_NUMBER,value).apply()

    private val PASSWORD = "password_last"
    var password: String?
        get() = sharedPreferences.getString(PASSWORD,null)
        set(value) = sharedPreferences.edit().putString(PASSWORD,value).apply()

    private val EDIT_PASSWORD = "edit_password_last"
    var editPassword: String?
        get() = sharedPreferences.getString(EDIT_PASSWORD,null)
        set(value) = sharedPreferences.edit().putString(EDIT_PASSWORD,value).apply()

    private val PIN = "pin"
    var pin: String?
        get() = sharedPreferences.getString(PIN,null)
        set(value) = sharedPreferences.edit().putString(PIN, value).apply()

}


