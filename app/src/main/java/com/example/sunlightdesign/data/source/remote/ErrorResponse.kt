package com.example.sunlightdesign.data.source.remote

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("message") val message: String?
)