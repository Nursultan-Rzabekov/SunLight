package com.corp.sunlightdesign.data.source.dataSource.remote.orders.entity

import com.google.gson.annotations.SerializedName

data class DeliveryServiceListResponse(
    @SerializedName("delivery_info")
    val deliveryServices: List<DeliveryServiceResponse>?
)