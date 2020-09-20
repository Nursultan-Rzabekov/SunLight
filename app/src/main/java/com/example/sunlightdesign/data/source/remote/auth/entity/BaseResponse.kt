package com.example.sunlightdesign.data.source.remote.auth.entity

open class BaseResponse{
    var errors: List<String>? = null
    var message: String? = null
    var error: String? = null
}