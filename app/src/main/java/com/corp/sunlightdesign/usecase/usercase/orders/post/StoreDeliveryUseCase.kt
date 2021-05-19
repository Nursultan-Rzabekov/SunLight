package com.corp.sunlightdesign.usecase.usercase.orders.post

import com.corp.sunlightdesign.data.source.OrdersRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.orders.entity.DeliverResponse
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase
import com.google.gson.annotations.SerializedName

class StoreDeliveryUseCase(
    private val repository: OrdersRepository
): BaseCoroutinesUseCase<DeliverResponse?>() {

    private var model: DeliverRequest? = null

    fun setModel(deliver: DeliverRequest) {
        this.model = deliver
    }

    override suspend fun executeOnBackground(): DeliverResponse? =
        model?.let {
            repository.storeDelivery(it)
        }

    data class DeliverRequest(

        var snl: String,

        @SerializedName("country_id")
        var countryId: Int,

        @SerializedName("region_id")
        var regionId: Int,

        @SerializedName("city_id")
        var cityId: Int,

        var street: String,

        var sum: String
    )
}