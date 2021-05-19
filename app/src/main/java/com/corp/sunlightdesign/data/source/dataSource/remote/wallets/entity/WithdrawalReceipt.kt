package com.corp.sunlightdesign.data.source.dataSource.remote.wallets.entity

import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.BaseResponse
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.User
import com.corp.sunlightdesign.data.source.dataSource.remote.orders.entity.Office

data class WithdrawalReceipt(
    var user: User,
    var withdraw: Withdraw,
    var office: Office
): BaseResponse()

data class Withdraw(
    val bv_value: String?,
    val cash_amount: String?,
    val created_at: String?,
    val currency_id: String?,
    val currency_value: String?,
    val id: Int?,
    val office_id: String?,
    val updated_at: String?,
    val user_id: String?,
    val withdraw_type: String?
)