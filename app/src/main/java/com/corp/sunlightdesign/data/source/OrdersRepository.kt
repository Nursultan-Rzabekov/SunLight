package com.corp.sunlightdesign.data.source

import com.corp.sunlightdesign.data.source.dataSource.CreateOrderPartner
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.OfficesList
import com.corp.sunlightdesign.data.source.dataSource.remote.orders.entity.*
import com.corp.sunlightdesign.usecase.usercase.orders.CalculateDeliveryUseCase
import com.corp.sunlightdesign.usecase.usercase.orders.post.StoreDeliveryUseCase

/**
 * Interface to the data layer.
 */
interface OrdersRepository {
    suspend fun getMyOrders(): Orders
    suspend fun getOrderById(id: Int)
    suspend fun getProductList(): OrderProducts
    suspend fun getProductByID(id: Int)
    suspend fun getOfficesList(): OfficesList
    suspend fun storeOrder(createOrderPartner: CreateOrderPartner): Orders
    suspend fun storeDelivery(delivery: StoreDeliveryUseCase.DeliverRequest): DeliverResponse
    suspend fun calculateDelivery(
        parameter: CalculateDeliveryUseCase.Request
    ): DeliveryServiceListResponse
}
