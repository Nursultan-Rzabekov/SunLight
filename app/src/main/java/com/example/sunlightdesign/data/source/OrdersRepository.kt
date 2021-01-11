package com.example.sunlightdesign.data.source

import com.example.sunlightdesign.data.source.dataSource.CreateOrderPartner
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.BaseResponse
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.CountriesList
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.OfficesList
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.DeliverResponse
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.Delivery
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.OrderProducts
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.Orders
import com.example.sunlightdesign.usecase.usercase.orders.post.StoreDeliveryUseCase

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
}
