package com.corp.sunlightdesign.data.source.repositories

import com.corp.sunlightdesign.data.source.OrdersRepository
import com.corp.sunlightdesign.data.source.dataSource.CreateEvent
import com.corp.sunlightdesign.data.source.dataSource.CreateOrderPartner
import com.corp.sunlightdesign.data.source.dataSource.remote.orders.OrdersServices
import com.corp.sunlightdesign.data.source.dataSource.remote.orders.entity.DeliverResponse
import com.corp.sunlightdesign.data.source.dataSource.remote.orders.entity.DeliveryServiceListResponse
import com.corp.sunlightdesign.data.source.dataSource.remote.orders.entity.Orders
import com.corp.sunlightdesign.usecase.usercase.orders.CalculateDeliveryUseCase
import com.corp.sunlightdesign.usecase.usercase.orders.post.StoreDeliveryUseCase


class DefaultOrdersRepository constructor(
    private val ordersServices: OrdersServices
) : OrdersRepository {

    override suspend fun getMyOrders(): Orders = ordersServices.getMyOrders().await()

    override suspend fun getOrderById(id: Int) = ordersServices.getOrderById(id)

    override suspend fun getProductList() = ordersServices.getProductList().await()

    override suspend fun getEventList() = ordersServices.getEventList().await()

    override suspend fun getProductByID(id: Int) = ordersServices.getProductByID(id)

    override suspend fun getOfficesList() = ordersServices.getOfficesList().await()

    override suspend fun storeOrder(createOrderPartner: CreateOrderPartner) =
        ordersServices.storeOrder(createOrderPartner).await()

    override suspend fun buyEvent(createEvent: CreateEvent) =
        ordersServices.buyEvent(id = createEvent.eventId, createEvent = createEvent).await()


    override suspend fun storeDelivery(delivery: StoreDeliveryUseCase.DeliverRequest): DeliverResponse =
        ordersServices.storeDelivery(delivery).await()

    override suspend fun calculateDelivery(
        parameter: CalculateDeliveryUseCase.Request
    ): DeliveryServiceListResponse = ordersServices.calculateDelivery(parameter).await()
}
