package com.corp.sunlightdesign.data.source.dataSource.remote.orders

import com.corp.sunlightdesign.data.source.dataSource.CreateEvent
import com.corp.sunlightdesign.data.source.dataSource.CreateOrderPartner
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.OfficesList
import com.corp.sunlightdesign.data.source.dataSource.remote.orders.entity.*
import com.corp.sunlightdesign.usecase.usercase.orders.CalculateDeliveryUseCase
import com.corp.sunlightdesign.usecase.usercase.orders.post.StoreDeliveryUseCase
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrdersServices {

    @GET("orders")
    fun getMyOrders(): Deferred<Orders>

    @GET("orders/{id}/show")
    fun getOrderById(@Path("id") id: Int)

    @GET("orders/products-list")
    fun getProductList(): Deferred<OrderProducts>

    @GET("cabinet/events")
    fun getEventList(): Deferred<OrderEvents>

    @GET("cabinet/events/my")
    fun getMyTicketsList(): Deferred<MyTickets>

    @GET("orders/product-list/show/{id}")
    fun getProductByID(@Path("id") id: Int)

    @GET("cabinet/add-partner/offices-list")
    fun getOfficesList(
    ): Deferred<OfficesList>

    @POST("orders/store")
    fun storeOrder(
        @Body createOrderPartner: CreateOrderPartner
    ): Deferred<Orders>

    @POST("cabinet/events/store/{id}")
    fun buyEvent(
        @Path("id") id: Int,
        @Body createEvent: CreateEvent
    ): Deferred<CreateEvent>

    @POST("orders/delivery")
    fun storeDelivery(
        @Body deliver: StoreDeliveryUseCase.DeliverRequest
    ): Deferred<DeliverResponse>

    @POST("delivery/calculate")
    fun calculateDelivery(
        @Body parameters: CalculateDeliveryUseCase.Request
    ): Deferred<DeliveryServiceListResponse>
}