package com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity


data class Login(
    val role: String?,
    val token: String?,
    val token_type: String?,
    val user: User?
) : BaseResponse()

data class User(
    val birthday: String?,
    val city_id: Int?,
    val country_id: Int?,
    val created_at: String?,
    val direct_id: Int?,
    val direct_level: Int?,
    val document_back_path: String?,
    val document_front_path: String?,
    val email: String?,
    val first_name: String?,
    val id: Int?,
    val iin: String?,
    val is_active: Int?,
    val last_login: String?,
    val last_name: String?,
    val left_children: String?,
    val left_total: String?,
    val level: Int?,
    val middle_name: String?,
    val office_id: Int?,
    val package_: Package?,
    val package_id: Int?,
    val parent_id: Int?,
    val parent_level: Int?,
    val permissions: Any?,
    val phone: String?,
    val phone_verified_at: String?,
    val position: String?,
    val referral_link: String?,
    val region_id: Int?,
    val register_by: Int?,
    val right_children: String?,
    val right_total: String?,
    val roles: List<Role>?,
    val root_id: Int?,
    val status: Status?,
    val status_id: Int?,
    val step: Int?,
    val system_status: Int?,
    val user_avatar_path: String?,
    val uuid: String?,
    val wallet: Wallet?
)

data class Role(
    val created_at: String?,
    val guard_name: String?,
    val id: Int?,
    val name: String?,
    val pivot: Pivot?,
    val slug: String?,
    val updated_at: String?
)

data class Status(
    val deleted_at: String?,
    val development_bonus_awards: Any?,
    val development_bonus_limits_per_status: Int?,
    val id: Int?,
    val low_branch_amount: Int?,
    val matching_bonus_depth: String?,
    val matching_bonus_percent: Int?,
    val purchases_cashback: Int?,
    val purchases_cashback_depth: String?,
    val status_description: String?,
    val status_name: String?,
    val team_bonus_limits_per_period: Int?,
    val team_bonus_percent: Int?,
    val updated_at: String?
)

data class Wallet(
    val created_at: String?,
    val deleted_at: String?,
    val id: Int?,
    val left_branch_total: Int?,
    val main_wallet: Double?,
    val purchase_wallet: Double?,
    val registry_wallet: Int?,
    val right_branch_total: Int?,
    val updated_at: String?,
    val user_id: Int?
)

data class Pivot(
    val model_id: Int?,
    val model_type: String?,
    val role_id: Int?,
    val package_id: Int,
    val product_id: Int?
)