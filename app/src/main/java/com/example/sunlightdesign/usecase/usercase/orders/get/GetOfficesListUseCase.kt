package com.example.sunlightdesign.usecase.usercase.orders.get

import com.example.sunlightdesign.data.source.OrdersRepository
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.OfficesList
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase

class GetOfficesListUseCase constructor(
    private val ordersRepository: OrdersRepository
): BaseCoroutinesUseCase<OfficesList>(){

    override suspend fun executeOnBackground() = ordersRepository.getOfficesList()
}