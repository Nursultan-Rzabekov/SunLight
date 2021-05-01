package com.example.sunlightdesign.data.source

import com.example.sunlightdesign.data.source.dataSource.CreateOrderPartner
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.BaseResponse
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.CountriesList
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.OfficesList
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.*
import com.example.sunlightdesign.usecase.usercase.orders.CalculateDeliveryUseCase
import com.example.sunlightdesign.usecase.usercase.orders.post.StoreDeliveryUseCase
import retrofit2.http.Query

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
