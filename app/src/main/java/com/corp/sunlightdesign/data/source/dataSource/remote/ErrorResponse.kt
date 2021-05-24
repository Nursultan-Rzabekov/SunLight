package com.corp.sunlightdesign.data.source.dataSource.remote

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("message") val message: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("logout") val logout: Boolean?
)

data class DefaultErrorResponse(
    @SerializedName("message") val message: String?
)

data class ErrorListResponse(
    @SerializedName("message") val message: String?,
    @SerializedName("errors") val errors: Map<String, List<String>>?,
    @SerializedName("error") val error: String?
)