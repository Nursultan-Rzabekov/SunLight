package com.example.sunlightdesign.data.source.dataSource.remote.orders.entity

data class Orders(
    val orders: List<Order>?
)

data class Order(
    val cashier: Any?,
    val created_at: String?,
    val id: Int?,
    val office: Office?,
    val order_finish_date: Any?,
    val order_payment_type: Int?,
    val order_payment_type_arr: String?,
    val order_payment_type_value: String?,
    val order_status: Int?,
    val order_status_arr: String?,
    val order_status_value: String?,
    val order_type: Int?,
    val order_type_arr: String?,
    val order_type_value: String?,
    val products: List<Product>?,
    val total_price: Int?,
    val user: User
)

data class Office(
    val address: String?,
    val city: City?,
    val city_id: Int?,
    val close_hours: String?,
    val country_id: Int?,
    val created_at: String?,
    val deleted_at: Any?,
    val id: Int?,
    val latitude: String?,
    val longitude: String?,
    val office_image_path: String?,
    val office_name: String?,
    val open_hours: String?,
    val phone: String?,
    val region_id: Int?,
    val updated_at: String?
)

data class Product(
    val created_at: String?,
    val deleted_at: Any?,
    val id: Int?,
    val is_sent: Int?,
    val order_id: Int?,
    val product: ProductX?,
    val product_id: Int?,
    val product_price: Int?,
    val product_price_in_bv: Int?,
    val product_price_in_currency: Int?,
    val product_quantity: Int?,
    val updated_at: String?
)

data class User(
    val birthday: Any?,
    val block_status: Int?,
    val childs: Any?,
    val city_id: Int?,
    val country_id: Int?,
    val created_at: String?,
    val direct_id: Int?,
    val direct_level: Int?,
    val document_back_path: String?,
    val document_front_path: String?,
    val email: String?,
    val first_name: String?,
    val id: Int?,
    val iin: String?,
    val is_active: Int?,
    val last_login: Any?,
    val last_name: String?,
    val left_children: Any?,
    val left_total: String?,
    val level: Int?,
    val middle_name: Any?,
    val office_id: Int?,
    val package_id: Int?,
    val parent_id: Int?,
    val parent_level: Int?,
    val permissions: Any?,
    val phone: String?,
    val phone_verified_at: String?,
    val position: String?,
    val referral_link: String?,
    val region_id: Int?,
    val register_by: Int?,
    val right_children: Any?,
    val right_total: String?,
    val root_id: Any?,
    val status_id: Int?,
    val step: Int?,
    val system_status: Int?,
    val user_avatar_path: Any?,
    val uuid: String?,
    val who: Int?
)

data class City(
    val city_code: Any?,
    val city_description: Any?,
    val city_name: String?,
    val country_id: Any?,
    val id: Int?,
    val region_id: Int?
)

data class ProductX(
    val created_at: String?,
    val deleted_at: Any?,
    val id: Int?,
    val product_description: String?,
    val product_image_back_path: String?,
    val product_image_front_path: String?,
    val product_name: String?,
    val product_price: Int?,
    val product_price_in_bv: Int?,
    val product_price_in_currency: Int?,
    val product_quantity: Int?,
    val product_short_description: String?,
    val product_type: Int?,
    val updated_at: String?
)