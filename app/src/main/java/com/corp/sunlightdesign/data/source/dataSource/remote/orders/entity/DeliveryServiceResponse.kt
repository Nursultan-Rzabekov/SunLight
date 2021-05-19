package com.corp.sunlightdesign.data.source.dataSource.remote.orders.entity

import com.google.gson.annotations.SerializedName

data class DeliveryServiceResponse(

    @SerializedName("delivery")
    val delivery: DeliveryService?,

    @SerializedName("price")
    val price: Double?,

    @SerializedName("price_in_bv")
    val priceBv: Double?,

    @SerializedName("delivery_estimate")
    val estimateTime: String?,

    @SerializedName("delivery_type_id")
    val deliveryTypeId: Int?,

    @SerializedName("delivery_zone_id")
    val deliveryZoneId: Int?
)

data class DeliveryService(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("alias")
    val alias: String?
)
