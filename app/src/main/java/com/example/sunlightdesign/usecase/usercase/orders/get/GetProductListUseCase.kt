package com.example.sunlightdesign.usecase.usercase.orders.get

import com.example.sunlightdesign.data.source.OrdersRepository
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.Orders
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase

class GetProductListUseCase constructor(
    private val ordersRepository: OrdersRepository
) : BaseCoroutinesUseCase<Unit>() {

    override suspend fun executeOnBackground(): Unit = ordersRepository.getProductList()
}