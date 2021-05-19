package com.corp.sunlightdesign.data.source.dataSource.remote.orders.entity

import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.BaseResponse

data class DeliverResponse(
    val delivery: Delivery?
): BaseResponse()

data class Delivery(
    val city_id: Int?,
    val city_name: String?,
    val country_id: Int?,
    val country_name: String?,
    val created_at: String?,
    val id: Int?,
    val region_id: Int?,
    val region_name: String?,
    val snl: String?,
    val street: String?,
    val sum: Int?,
    val updated_at: String?,
    val user_id: Int?
)