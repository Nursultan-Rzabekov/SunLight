package com.corp.sunlightdesign.usecase.usercase.orders.get

import com.corp.sunlightdesign.data.source.OrdersRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.orders.entity.OrderProducts
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase

class GetProductListUseCase constructor(
    private val ordersRepository: OrdersRepository
) : BaseCoroutinesUseCase<OrderProducts>() {

    override suspend fun executeOnBackground(): OrderProducts = ordersRepository.getProductList()
}