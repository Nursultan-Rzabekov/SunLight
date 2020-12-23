package com.example.sunlightdesign.data.source.dataSource.remote.profile.entity

data class InvitedResponse(
    val children: Children?
)

data class Children(
    val current_page: Int?,
    val `data`: List<Child>?,
    val first_page_url: String?,
    val from: Int?,
    val last_page: Int?,
    val last_page_url: String?,
    val next_page_url: String?,
    val path: String?,
    val per_page: Int?,
    val prev_page_url: Any?,
    val to: Int?,
    val total: Int?
)

data class Data(
    val birthday: Any?,
    val block_status: Int?,
    val childs: Int?,
    val city_id: Int?,
    val country_id: Int?,
    val created_at: String?,
    val direct_id: Int?,
    val direct_level: Int?,
    val document_back_path: String?,
    val document_front_path: String?,
    val email: Any?,
    val first_name: String?,
    val id: Int?,
    val iin: String?,
    val is_active: Int?,
    val last_login: Any?,
    val last_name: String?,
    val left_children: Any?,
    val left_total: String?,
    val level: Int?,
    val middle_name: String?,
    val office_id: Int?,
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
    val right_children: Any?,
    val right_total: String?,
    val root_id: Any?,
    val status_id: Int?,
    val status_update_date: String?,
    val step: Int?,
    val system_status: Int?,
    val user_avatar_path: String?,
    val uuid: String?,
    val wallet_main_wallet: Double?,
    val who: String?,
    val withdraw_status: Int?
)