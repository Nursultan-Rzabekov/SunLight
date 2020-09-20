package com.example.sunlightdesign.data.source.remote.auth.entity

data class PackagesList(
    val message: String?,
    val packages: List<Package>?
)


data class Product(
    val created_at: String?,
    val deleted_at: String?,
    val id: Int?,
    val pivot: Pivot?,
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

