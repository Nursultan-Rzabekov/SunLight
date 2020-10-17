package com.example.sunlightdesign.data.source.repositories

import com.example.sunlightdesign.data.source.OrdersRepository
import com.example.sunlightdesign.data.source.dataSource.remote.orders.OrdersServices
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.Orders


class DefaultOrdersRepository constructor(
    private val ordersServices: OrdersServices
) : OrdersRepository {

    override suspend fun getMyOrders(): Orders = ordersServices.getMyOrders().await()

    override suspend fun getOrderById(id: Int) = ordersServices.getOrderById(id)

    override suspend fun getProductList() = ordersServices.getProductList().await()

    override suspend fun getProductByID(id: Int) = ordersServices.getProductByID(id)


}
