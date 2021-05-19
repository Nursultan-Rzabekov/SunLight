package com.corp.sunlightdesign.usecase.usercase.orders.post

import com.corp.sunlightdesign.data.source.OrdersRepository
import com.corp.sunlightdesign.data.source.dataSource.CreateOrderPartner
import com.corp.sunlightdesign.data.source.dataSource.remote.orders.entity.Orders
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase

class StoreOrderUseCase(
    private val ordersRepository: OrdersRepository
) : BaseCoroutinesUseCase<Orders?>(){

    private var createOrderPartner: CreateOrderPartner? = null
    fun setData(createOrderPartner: CreateOrderPartner){
        this.createOrderPartner = createOrderPartner
    }
    override suspend fun executeOnBackground(): Orders? = this.createOrderPartner?.let {
        ordersRepository.storeOrder(createOrderPartner = it)
    }
}