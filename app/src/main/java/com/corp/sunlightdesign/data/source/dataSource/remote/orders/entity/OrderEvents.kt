package com.corp.sunlightdesign.data.source.dataSource.remote.orders.entity

import com.google.gson.annotations.SerializedName

data class OrderEvents(
    val events: List<Event>?
)

data class Event(
    val id: Int?,
    val name: String?,
    val image: String?,
    @SerializedName("price_adult")
    val priceAdult: Int?,
    @SerializedName("price_child")
    val priceChild: Int?,
    @SerializedName("started_at")
    val startedAt: String?,
    val description: String
)