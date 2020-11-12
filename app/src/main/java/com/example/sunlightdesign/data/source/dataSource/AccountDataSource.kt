package com.example.sunlightdesign.data.source.dataSource

import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.*

interface AccountDataSource {
    suspend fun getCountriesList(): CountriesList
    suspend fun getUsersList(): UsersList
    suspend fun addPartner(body: AddPartner): Login
    suspend fun setPackage(package_id: Int, user_id: Int): User
    suspend fun getPackagesList(): PackagesList
    suspend fun getOfficesList(): OfficesList
    suspend fun createOrder(createOrderPartner: CreateOrderPartner): AddPartnerResponse
}

data class AddPartner(
    val first_name: String?,
    val last_name: String?,
    val phone: String?,
    val middle_name: String?,
    val country_id: Int?,
    val region_id: Int?,
    val city_id: Int?,
    val iin: String?,
    val register_by: Int?,
    val position: String?
)

data class CreateOrderPartner(
    val user_id: Int,
    val office_id: Int,
    val order_payment_type: Int,
    val products: List<Product>
) {
    class Builder {
        var user_id: Int = -1
        var office_id: Int = -1
        var order_payment_type: Int = -1
        var products: List<Product> = listOf()

        fun build(): CreateOrderPartner = CreateOrderPartner(
            user_id, office_id, order_payment_type, products
        )
    }
}

data class ItemId(
    val id: Int?
){
    class Builder {
        var id: Int = -1

        fun build() = ItemId(id)
    }

}
