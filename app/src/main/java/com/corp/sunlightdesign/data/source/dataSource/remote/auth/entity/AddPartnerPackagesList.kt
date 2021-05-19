package com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity

import com.google.gson.annotations.SerializedName

data class AddPartnerPackagesList(
    val message: String?,
    @SerializedName("package")
    val packages: Package?
)

data class Package(
    val deleted_at: String?,
    val id: Int?,
    val package_description: String?,
    val package_name: String?,
    val package_price: Double?,
    val package_price_in_currency: Double?,
    val package_price_in_kzt: Double?,
    val package_reference: Double?,
    val products: List<Product>?,
    val updated_at: String?,
    var isChecked: Boolean = false
)