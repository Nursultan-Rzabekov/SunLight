package com.corp.sunlightdesign.usecase.usercase.orders.get

import com.corp.sunlightdesign.data.source.OrdersRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.orders.entity.MyTickets
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase


class GetMyTicketsUseCase constructor(
    private val ordersRepository: OrdersRepository
) : BaseCoroutinesUseCase<MyTickets?>() {

    override suspend fun executeOnBackground() = ordersRepository.getMyTicketsList()
}

