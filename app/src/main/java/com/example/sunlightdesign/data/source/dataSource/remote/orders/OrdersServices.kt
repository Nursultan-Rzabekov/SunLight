package com.example.sunlightdesign.data.source.dataSource.remote.orders

import com.example.sunlightdesign.data.source.dataSource.CreateOrderPartner
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.*
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.DeliverResponse
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.OrderProducts
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.Orders
import com.example.sunlightdesign.usecase.usercase.orders.post.StoreDeliveryUseCase
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface OrdersServices {

    @GET("orders")
    fun getMyOrders(): Deferred<Orders>

    @GET("orders/{id}/show")
    fun getOrderById(@Path("id") id: Int)

    @GET("orders/products-list")
    fun getProductList(): Deferred<OrderProducts>

    @GET("orders/product-list/show/{id}")
    fun getProductByID(@Path("id") id: Int)

    @GET("cabinet/add-partner/offices-list")
    fun getOfficesList(
    ): Deferred<OfficesList>

    @POST("orders/store")
    fun storeOrder(
        @Body createOrderPartner: CreateOrderPartner
    ): Deferred<Orders>

    @POST("orders/delivery")
    fun storeDelivery(
        @Body deliver: StoreDeliveryUseCase.DeliverRequest
    ): Deferred<DeliverResponse>
}