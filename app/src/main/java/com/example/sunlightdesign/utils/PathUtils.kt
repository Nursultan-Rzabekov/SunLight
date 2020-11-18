package com.example.sunlightdesign.utils

import com.example.sunlightdesign.BuildConfig

fun getImageUrl(sublink: String?) : String {
    return BuildConfig.BASE_URL_IMAGE + sublink
}