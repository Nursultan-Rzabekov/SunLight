package com.example.sunlightdesign.data.source.dataSource.remote.email.entity

data class Announcements(
    val announcements: AnnouncementsX?,
    val user: User?
)

data class AnnouncementsX(
    val current_page: Int?,
    val data: List<Data>?,
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

data class User(
    val birthday: Any?,
    val block_status: Int?,
    val childs: Any?,
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
    val last_login: Any?,
    val last_name: String?,
    val left_children: Any?,
    val left_total: String?,
    val level: Int?,
    val middle_name: Any?,
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
    val step: Int?,
    val system_status: Int?,
    val user_avatar_path: Any?,
    val uuid: String?,
    val who: Int?
)

data class Data(
    val created_at: String?,
    val id: Int?,
    val message_body: String?,
    val message_title: String?,
    val updated_at: String?,
    val user_id: Int?,
    val read: Int?
)