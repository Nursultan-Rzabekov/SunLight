package com.example.sunlightdesign.data.source.dataSource.remote.profile.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class UserInfo(
    val children: List<Child>?,
    val lastAnnouncement: LastAnnouncement?,
    val parent: Parent?,
    val user: User?,
    val weekBonus: List<Int>?
)

data class LastAnnouncement(
    val announcement_id: Int?,
    val created_at: String?,
    val deleted_at: String?,
    val id: Int?,
    val is_archived: Int?,
    val is_read: Int?,
    val updated_at: String?,
    val user_id: Int?
)

data class Parent(
    val birthday: String?,
    val block_status: Int?,
    val childs: Int?,
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
    val left_children: List<Child>?,
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
    val status: Status?,
    val status_id: Int?,
    val step: Int?,
    val system_status: Int?,
    val user_avatar_path: Any?,
    val uuid: String?,
    val who: Any?
)

data class Child(
    val block_status: Int?,
    val childs: Int?,
    val city_id: Int?,
    val country_id: Int?,
    val created_at: String?,
    val direct_id: Int?,
    val direct_level: Int?,
    val document_back_path: String?,
    val document_front_path: String?,
    val first_name: String?,
    val id: Int?,
    val iin: String?,
    val is_active: Int?,
    val last_name: String?,
    val left_total: String?,
    val level: Int?,
    val middle_name: String?,
    val office_id: Int?,
    val package_id: Int?,
    val parent_id: Int?,
    val parent_level: Int?,
    val phone: String?,
    val phone_verified_at: String?,
    val position: String?,
    val referral_link: String?,
    val region_id: Int?,
    val register_by: Int?,
    val right_total: String?,
    val status_id: Int?,
    val step: Int?,
    val system_status: Int?,
    val uuid: String?,
    val wallet_main_wallet: Int?
)

data class User(
    val birthday: String?,
    val block_status: Int?,
    val childs: Any?,
    val city: City?,
    val city_id: Int?,
    val country: Country?,
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
    val region: Region?,
    val region_id: Int?,
    val register_by: Int?,
    val right_children: Any?,
    val right_total: String?,
    val root_id: Any?,
    val status: StatusX?,
    val status_id: Int?,
    val step: Int?,
    val system_status: Int?,
    val user_avatar_path: Any?,
    val uuid: String?,
    val wallet: Wallet?,
    val week_bonus: Any?,
    val who: Int?
)

data class Status(
    val deleted_at: Any?,
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

data class City(
    val city_code: Any?,
    val city_description: Any?,
    val city_name: String?,
    val country_id: Any?,
    val id: Int?,
    val region_id: Int?
)

data class Country(
    val country_code: String?,
    val country_name: String?,
    val currency_id: Int?,
    val id: Int?
)

data class Region(
    val country_id: Int?,
    val id: Int?,
    val region_code: Any?,
    val region_name: String?
)

data class StatusX(
    val deleted_at: Any?,
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
    val deleted_at: Any?,
    val id: Int?,
    val left_branch_total: Int?,
    val main_wallet: Int?,
    val purchase_wallet: Int?,
    val registry_wallet: Int?,
    val right_branch_total: Int?,
    val updated_at: String?,
    val user_id: Int?
)

@Parcelize
data class ShortenedUserInfo(
    val birthday: String?,
    val cityName: String?,
    val countryName: String?,
    val regionName: String?,
    val fullName: String?,
    val uuid: String?,
    val status: String?,
    val phone: String?,
    val document_back_path: String?,
    val document_front_path: String?
): Parcelable