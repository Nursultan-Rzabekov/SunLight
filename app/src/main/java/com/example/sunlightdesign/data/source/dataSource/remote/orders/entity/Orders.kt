package com.example.sunlightdesign.data.source.dataSource.remote.orders.entity

data class Orders(
    val orders: List<Order>?
)

data class Order(
    val cashier: Any?,
    val created_at: String?,
    val id: Double?,
    val office: Office?,
    val order_finish_date: Any?,
    val order_payment_type: Double?,
    val order_payment_type_arr: String?,
    val order_payment_type_value: String?,
    val order_status: Double?,
    val order_status_arr: String?,
    val order_status_value: String?,
    val order_type: Double?,
    val order_type_arr: String?,
    val order_type_value: String?,
    val products: List<Product>?,
    val total_price: Double?,
    val user: User
)

data class Office(
    val address: String?,
    val city: City?,
    val city_id: Double?,
    val close_hours: String?,
    val country_id: Double?,
    val created_at: String?,
    val deleted_at: Any?,
    val id: Double?,
    val latitude: String?,
    val longitude: String?,
    val office_image_path: String?,
    val office_name: String?,
    val open_hours: String?,
    val phone: String?,
    val region_id: Double?,
    val updated_at: String?
)

data class Product(
    val created_at: String?,
    val deleted_at: Any?,
    val id: Double?,
    val is_sent: Double?,
    val order_id: Double?,
    val product: com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Product?,
    val product_id: Double?,
    val product_price: Double?,
    val product_price_in_bv: Double?,
    val product_price_in_currency: Double?,
    val product_quantity: Double?,
    val updated_at: String?,
    var isChecked: Boolean = false
)

data class User(
    val birthday: Any?,
    val block_status: Double?,
    val childs: Any?,
    val city_id: Double?,
    val country_id: Double?,
    val created_at: String?,
    val direct_id: Double?,
    val direct_level: Double?,
    val document_back_path: String?,
    val document_front_path: String?,
    val email: String?,
    val first_name: String?,
    val id: Double?,
    val iin: String?,
    val is_active: Double?,
    val last_login: Any?,
    val last_name: String?,
    val left_children: Any?,
    val left_total: String?,
    val level: Double?,
    val middle_name: Any?,
    val office_id: Double?,
    val package_id: Double?,
    val parent_id: Double?,
    val parent_level: Double?,
    val permissions: Any?,
    val phone: String?,
    val phone_verified_at: String?,
    val position: String?,
    val referral_link: String?,
    val region_id: Double?,
    val register_by: Double?,
    val right_children: Any?,
    val right_total: String?,
    val root_id: Any?,
    val status_id: Double?,
    val step: Double?,
    val system_status: Double?,
    val user_avatar_path: Any?,
    val uuid: String?,
    val who: Double?
)

data class City(
    val city_code: Any?,
    val city_description: Any?,
    val city_name: String?,
    val country_id: Any?,
    val id: Double?,
    val region_id: Double?
)