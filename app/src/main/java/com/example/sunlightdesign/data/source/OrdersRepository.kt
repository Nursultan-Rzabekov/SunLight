package com.example.sunlightdesign.data.source

import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.Orders

/**
 * Interface to the data layer.
 */
interface OrdersRepository {
    suspend fun getMyOrders(): Orders
    suspend fun getOrderById(id: Int)
    suspend fun getProductList()
    suspend fun getProductByID(id: Int)
}
