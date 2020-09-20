package com.example.sunlightdesign.data.source.remote.auth.entity

open class BaseResponse{
    var errors:  Errors? = null
    var message: String? = null
    var error: String? = null
}

data class Errors(
    val phone: List<String>
)