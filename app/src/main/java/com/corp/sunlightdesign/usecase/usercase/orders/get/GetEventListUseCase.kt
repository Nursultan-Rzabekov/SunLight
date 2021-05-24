package com.corp.sunlightdesign.usecase.usercase.orders.get

import com.corp.sunlightdesign.data.source.OrdersRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.orders.entity.OrderEvents
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase

class GetEventListUseCase constructor(
    private val ordersRepository: OrdersRepository
) : BaseCoroutinesUseCase<OrderEvents>() {

    override suspend fun executeOnBackground() = ordersRepository.getEventList()
}