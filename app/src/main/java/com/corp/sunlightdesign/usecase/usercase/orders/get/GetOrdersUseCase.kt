package com.corp.sunlightdesign.usecase.usercase.orders.get

import com.corp.sunlightdesign.data.source.OrdersRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.orders.entity.Orders
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase


class GetOrdersUseCase constructor(
    private val ordersRepository: OrdersRepository
) : BaseCoroutinesUseCase<Orders?>() {

    override suspend fun executeOnBackground(): Orders? = ordersRepository.getMyOrders()
}

