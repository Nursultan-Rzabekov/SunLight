package com.example.sunlightdesign.data.source.dataSource

import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.*
import okhttp3.MultipartBody

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
    val first_name: String,
    val last_name: String,
    val phone: String,
    val middle_name: String,
    val country_id: Int,
    val region_id: Int,
    val city_id: Int,
    val iin: String,
    val register_by: Int,
    val position: String,
    val document_front: MultipartBody.Part? = null,
    val document_back: MultipartBody.Part? = null
)

data class CreateOrderPartner(
    val user_id: Int,
    val office_id: Int,
    val order_payment_type: Int,
    val payment_sum: Double,
    val products: List<Product>,
    val delivery_id: Int?,
    val delivery_type: Int
) {
    companion object {
        const val DELIVERY_TYPE_BY_COMPANY = 1
        const val DELIVERY_TYPE_PICKUP = 2
    }

    class Builder {
        var userId: Int = -1
        var officeId: Int = 0
        var orderPaymentType: Int = -1
        var products: List<Product> = listOf()
        var paymentSum: Double = -0.0
        var deliveryId: Int? = null
        var deliveryType: Int = -1

        fun build(): CreateOrderPartner = CreateOrderPartner(
            user_id = userId,
            office_id = officeId,
            order_payment_type = orderPaymentType,
            products = products,
            payment_sum = paymentSum,
            delivery_id = deliveryId,
            delivery_type = deliveryType
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
