package com.corp.sunlightdesign.usecase.usercase.orders.get

import com.corp.sunlightdesign.data.source.OrdersRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.OfficesList
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase

class GetOfficesListUseCase constructor(
    private val ordersRepository: OrdersRepository
): BaseCoroutinesUseCase<OfficesList>(){

    override suspend fun executeOnBackground() = ordersRepository.getOfficesList()
}