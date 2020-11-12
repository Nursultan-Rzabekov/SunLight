package com.example.sunlightdesign.usecase.usercase.orders.post

import com.example.sunlightdesign.data.source.OrdersRepository
import com.example.sunlightdesign.data.source.dataSource.CreateOrderPartner
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase

class StoreOrderUseCase(
    private val ordersRepository: OrdersRepository
) : BaseCoroutinesUseCase<String?>(){

    private var createOrderPartner: CreateOrderPartner? = null
    fun setData(createOrderPartner: CreateOrderPartner){
        this.createOrderPartner = createOrderPartner
    }
    override suspend fun executeOnBackground(): String? = this.createOrderPartner?.let {
        ordersRepository.storeOrder(createOrderPartner = it)
    }
}