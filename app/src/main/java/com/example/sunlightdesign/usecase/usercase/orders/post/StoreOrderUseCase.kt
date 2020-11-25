package com.example.sunlightdesign.usecase.usercase.orders.post

import com.example.sunlightdesign.data.source.OrdersRepository
import com.example.sunlightdesign.data.source.dataSource.CreateOrderPartner
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.Orders
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase

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