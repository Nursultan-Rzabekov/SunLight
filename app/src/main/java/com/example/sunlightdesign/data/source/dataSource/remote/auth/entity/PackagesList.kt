package com.example.sunlightdesign.data.source.dataSource.remote.auth.entity

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
    val product_price: Double?,
    val product_price_in_bv: Double?,
    val product_price_in_currency: Double?,
    var product_quantity: Int?,
    val product_short_description: String?,
    val product_type: Double?,
    val updated_at: String?,
    var isChecked: Boolean = false
)

