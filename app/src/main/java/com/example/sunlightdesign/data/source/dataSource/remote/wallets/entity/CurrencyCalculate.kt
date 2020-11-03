package com.example.sunlightdesign.data.source.dataSource.remote.wallets.entity

import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.User

data class CurrencyCalculate(
    val currencies: List<CurrencyX>?,
    val user: User
)

data class CurrencyX(
    val created_at: String?,
    val currency_bv_value: Int?,
    val currency_code: String?,
    val currency_name: String?,
    val currency_sign: String?,
    val currency_value: Int?,
    val deleted_at: String?,
    val id: Int?,
    val updated_at: String?
) {
    override fun toString(): String = this.currency_code.toString()
}