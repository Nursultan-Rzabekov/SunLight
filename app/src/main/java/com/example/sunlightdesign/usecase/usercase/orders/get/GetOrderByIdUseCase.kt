package com.example.sunlightdesign.usecase.usercase.orders.get

import com.example.sunlightdesign.data.source.OrdersRepository
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase

class GetOrderByIdUseCase constructor(
    private val ordersRepository: OrdersRepository
) : BaseCoroutinesUseCase<Unit?>() {

    private var id: Int? = null
    fun setData(id: Int) {
        this.id = id
    }

    override suspend fun executeOnBackground(): Unit? =
        this.id?.let { ordersRepository.getOrderById(id = it) }

}