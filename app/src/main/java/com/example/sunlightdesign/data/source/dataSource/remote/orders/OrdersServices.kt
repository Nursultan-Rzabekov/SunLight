package com.example.sunlightdesign.data.source.dataSource.remote.orders

import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.OfficesList
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Product
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.User
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.OrderProducts
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.Orders
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

    @FormUrlEncoded
    @POST("orders/store")
    fun storeOrder(
        @Field("office_id") office_id: Int,
        @Field("order_payment_type") order_payment_type: Int,
        @Field("order_type") order_type: Int,
        @Field("products") products: List<Product>,
        @Field("total_in_bv") total_in_bv: Int,
        @Field("user_id") user_id: Int
    ): Deferred<String>

}