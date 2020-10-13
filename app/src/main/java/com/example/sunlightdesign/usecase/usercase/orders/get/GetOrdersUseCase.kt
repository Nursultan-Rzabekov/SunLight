package com.example.sunlightdesign.usecase.usercase.orders.get

import com.example.sunlightdesign.data.source.OrdersRepository
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.Orders
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase


class GetOrdersUseCase constructor(
    private val ordersRepository: OrdersRepository
) : BaseCoroutinesUseCase<Orders?>() {

    override suspend fun executeOnBackground(): Orders? = ordersRepository.getMyOrders()
}

