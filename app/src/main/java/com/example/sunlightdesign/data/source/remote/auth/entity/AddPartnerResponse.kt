package com.example.sunlightdesign.data.source.remote.auth.entity

data class AddPartnerResponse(
    val message: String,
    val order: Order
)

data class Order(
    val created_at: String,
    val id: Int,
    val office_id: Int,
    val order_payment_type: String,
    val order_products: List<OrderProduct>,
    val order_type: Int,
    val updated_at: String,
    val user_id: Int
)

data class OrderProduct(
    val created_at: String,
    val deleted_at: Any,
    val id: Int,
    val is_sent: Int,
    val order_id: Int,
    val product: Product,
    val product_id: Int,
    val product_price: Int,
    val product_price_in_bv: Int,
    val product_price_in_currency: Int,
    val product_quantity: Int,
    val updated_at: String
)
