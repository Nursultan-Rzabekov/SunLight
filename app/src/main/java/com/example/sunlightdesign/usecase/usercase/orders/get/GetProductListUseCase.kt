package com.example.sunlightdesign.usecase.usercase.orders.get

import com.example.sunlightdesign.data.source.OrdersRepository
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.OrderProducts
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase

class GetProductListUseCase constructor(
    private val ordersRepository: OrdersRepository
) : BaseCoroutinesUseCase<OrderProducts>() {

    override suspend fun executeOnBackground(): OrderProducts = ordersRepository.getProductList()
}