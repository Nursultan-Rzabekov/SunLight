package com.example.sunlightdesign.data.source.remote.entity

data class LoginResponse(
    val message: String,
    val role: String,
    val token: String,
    val token_type: String,
    val user: User
)

data class User(
    val birthday: Any,
    val city_id: Int,
    val country_id: Int,
    val created_at: String,
    val direct_id: Any,
    val direct_level: Int,
    val document_back_path: String,
    val document_front_path: String,
    val email: String,
    val first_name: String,
    val id: Int,
    val iin: String,
    val is_active: Int,
    val last_login: Any,
    val last_name: String,
    val left_children: Any,
    val left_total: String,
    val level: Int,
    val middle_name: String,
    val office_id: Any,
    val package_: Package,
    val package_id: Int,
    val parent_id: Any,
    val parent_level: Int,
    val permissions: Any,
    val phone: String,
    val phone_verified_at: String,
    val position: String,
    val referral_link: String,
    val region_id: Any,
    val register_by: Int,
    val right_children: Any,
    val right_total: String,
    val roles: List<Role>,
    val root_id: Any,
    val status: Status,
    val status_id: Int,
    val step: Int,
    val system_status: Int,
    val user_avatar_path: Any,
    val uuid: String,
    val wallet: Wallet
)

data class Package(
    val deleted_at: Any,
    val id: Int,
    val package_description: String,
    val package_name: String,
    val package_price: Int,
    val package_price_in_currency: Double,
    val package_price_in_kzt: Int,
    val package_reference: Double,
    val updated_at: String
)

data class Role(
    val created_at: String,
    val guard_name: String,
    val id: Int,
    val name: String,
    val pivot: Pivot,
    val slug: String,
    val updated_at: String
)

data class Status(
    val deleted_at: Any,
    val development_bonus_awards: Any,
    val development_bonus_limits_per_status: Int,
    val id: Int,
    val low_branch_amount: Int,
    val matching_bonus_depth: String,
    val matching_bonus_percent: Int,
    val purchases_cashback: Int,
    val purchases_cashback_depth: String,
    val status_description: String,
    val status_name: String,
    val team_bonus_limits_per_period: Int,
    val team_bonus_percent: Int,
    val updated_at: String
)

data class Wallet(
    val created_at: String,
    val deleted_at: Any,
    val id: Int,
    val left_branch_total: Int,
    val main_wallet: Double,
    val purchase_wallet: Double,
    val registry_wallet: Int,
    val right_branch_total: Int,
    val updated_at: String,
    val user_id: Int
)

data class Pivot(
    val model_id: Int,
    val model_type: String,
    val role_id: Int
)