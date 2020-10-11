package com.example.sunlightdesign.data.source.dataSource.remote.email.entity

data class AnnouncementItem(
    val announcement: Announcement
)

data class Announcement(
    val author: Author,
    val created_at: String,
    val files: List<File>,
    val id: Int,
    val message_body: String,
    val message_title: String,
    val updated_at: String,
    val user_id: Int
)

data class Author(
    val birthday: Any,
    val block_status: Int,
    val childs: Int,
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
    val package_id: Int,
    val parent_id: Any,
    val parent_level: Int,
    val permissions: Any,
    val phone: String,
    val phone_verified_at: String,
    val position: String,
    val referral_link: String,
    val region_id: Any,
    val register_by: Any,
    val right_children: Any,
    val right_total: String,
    val root_id: Any,
    val status_id: Int,
    val step: Int,
    val system_status: Int,
    val user_avatar_path: Any,
    val uuid: String,
    val who: Any
)

data class File(
    val announcement_id: Int,
    val attached_file_extension: String,
    val attached_file_name: String,
    val attached_file_path: String,
    val attached_file_size: String,
    val created_at: String,
    val deleted_at: Any,
    val id: Int,
    val updated_at: String
)