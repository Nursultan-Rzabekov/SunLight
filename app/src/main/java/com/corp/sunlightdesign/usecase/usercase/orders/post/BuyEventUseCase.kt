package com.corp.sunlightdesign.usecase.usercase.orders.post

import com.corp.sunlightdesign.data.source.OrdersRepository
import com.corp.sunlightdesign.data.source.dataSource.CreateEvent
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase

class BuyEventUseCase(
    private val ordersRepository: OrdersRepository
) : BaseCoroutinesUseCase<CreateEvent?>() {

    private var createEvent: CreateEvent? = null
    fun setData(createEvent: CreateEvent) {
        this.createEvent = createEvent
    }

    override suspend fun executeOnBackground(): CreateEvent? = this.createEvent?.let {
        ordersRepository.buyEvent(createEvent = it)
    }
}