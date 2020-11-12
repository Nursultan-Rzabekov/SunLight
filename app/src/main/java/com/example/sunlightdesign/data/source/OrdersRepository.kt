package com.example.sunlightdesign.data.source

import com.example.sunlightdesign.data.source.dataSource.CreateOrderPartner
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.OrderProducts
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.Orders

/**
 * Interface to the data layer.
 */
interface OrdersRepository {
    suspend fun getMyOrders(): Orders
    suspend fun getOrderById(id: Int)
    suspend fun getProductList(): OrderProducts
    suspend fun getProductByID(id: Int)
    suspend fun storeOrder(createOrderPartner: CreateOrderPartner): String
}
