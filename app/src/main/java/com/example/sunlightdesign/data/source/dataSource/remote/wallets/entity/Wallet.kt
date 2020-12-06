package com.example.sunlightdesign.data.source.dataSource.remote.wallets.entity

data class Wallet(
    val user: User,
    val wallet: WalletX,
    val walletHistory: WalletHistory
)

data class User(
    val birthday: Any,
    val block_status: Double,
    val childs: Any,
    val city_id: Double,
    val country_id: Double,
    val created_at: String,
    val direct_id: Double,
    val direct_level: Double,
    val document_back_path: String,
    val document_front_path: String,
    val email: String,
    val first_name: String,
    val id: Double,
    val iin: String,
    val is_active: Double,
    val last_login: Any,
    val last_name: String,
    val left_children: Any,
    val left_total: String,
    val level: Double,
    val middle_name: Any,
    val office_id: Double,
    val package_id: Double,
    val parent_id: Double,
    val parent_level: Double,
    val permissions: Any,
    val phone: String,
    val phone_verified_at: String,
    val position: String,
    val referral_link: String,
    val region_id: Double,
    val register_by: Double,
    val right_children: Any,
    val right_total: String,
    val root_id: Any,
    val status_id: Double,
    val step: Double,
    val system_status: Double,
    val user_avatar_path: String,
    val uuid: String,
    val who: Double
)

data class WalletX(
    val created_at: String,
    val deleted_at: Any,
    val id: Double,
    val left_branch_total: Double,
    val main_wallet: Double,
    val purchase_wallet: Double,
    val registry_wallet: Double,
    val right_branch_total: Double,
    val updated_at: String,
    val user_id: Double
)

data class WalletHistory(
    val current_page: Double,
    val `data`: List<Data>,
    val first_page_url: String,
    val from: Double,
    val last_page: Double,
    val last_page_url: String,
    val next_page_url: Any,
    val path: String,
    val per_page: Double,
    val prev_page_url: Any,
    val to: Double,
    val total: Double
)

data class Data(
    val bonus: Bonus?,
    val created_at: String,
    val extra_data: String,
    val finish_date: String,
    val history_type: String,
    val id: Double?,
    val main_wallet_new: Double,
    val main_wallet_old: Double,
    val order: Any?,
    val purchase_wallet_new: Double,
    val purchase_wallet_old: Double,
    val registry_wallet_new: Double,
    val registry_wallet_old: Double,
    val start_date: String,
    val status_type: String,
    val user_id: Double,
    val value: Double,
    val wallet_id: Double,
    val wallet_type: String,
    val withdraw: Any?
)

data class Bonus(
    val bonus_description: String,
    val bonus_name: String,
    val bonus_type: Double,
    val created_at: String,
    val id: Double,
    val updated_at: String
)