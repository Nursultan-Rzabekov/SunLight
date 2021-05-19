package com.corp.sunlightdesign.utils

import com.corp.sunlightdesign.BuildConfig

fun getImageUrl(sublink: String?) : String {
    return BuildConfig.BASE_URL_IMAGE + sublink
}