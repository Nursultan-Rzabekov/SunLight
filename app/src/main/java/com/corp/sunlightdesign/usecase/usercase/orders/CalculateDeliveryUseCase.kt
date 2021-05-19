package com.corp.sunlightdesign.usecase.usercase.orders

import com.corp.sunlightdesign.data.source.OrdersRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.orders.entity.DeliveryServiceListResponse
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase
import com.google.gson.annotations.SerializedName

class CalculateDeliveryUseCase(
    private val repository: OrdersRepository
): BaseCoroutinesUseCase<DeliveryServiceListResponse?>() {

    private var model: Request? = null

    fun setModel(model: Request) {
        this.model = model
    }

    override suspend fun executeOnBackground(): DeliveryServiceListResponse? = this.model?.let {
        repository.calculateDelivery(it)
    }

    data class Request(
        @SerializedName("city_id")
        val cityId: Int,
        @SerializedName("country_code")
        val countryCode: String,
        @SerializedName("sum")
        val totalAmount: Double,
        val weight: String
    )
}