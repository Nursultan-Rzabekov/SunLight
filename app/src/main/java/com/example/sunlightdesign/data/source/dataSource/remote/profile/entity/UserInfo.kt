package com.example.sunlightdesign.data.source.dataSource.remote.profile.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class UserInfo(
    val children: List<Child>?,
    val lastAnnouncement: LastAnnouncement?,
    val parent: Parent?,
    val user: User?,
    val weekBonus: List<Double>?
)

data class LastAnnouncement(
    val announcement_id: Double?,
    val created_at: String?,
    val deleted_at: String?,
    val id: Double?,
    val is_archived: Double?,
    val is_read: Double?,
    val updated_at: String?,
    val user_id: Double?
)

data class Parent(
    val birthday: String?,
    val block_status: Double?,
    val childs: Double?,
    val city_id: Double?,
    val country_id: Double?,
    val created_at: String?,
    val direct_id: Double?,
    val direct_level: Double?,
    val document_back_path: String?,
    val document_front_path: String?,
    val email: String?,
    val first_name: String?,
    val id: Double?,
    val iin: String?,
    val is_active: Double?,
    val last_login: String?,
    val last_name: String?,
    val left_children: List<Child>?,
    val left_total: String?,
    val level: Double?,
    val middle_name: String?,
    val office_id: Double?,
    val package_id: Double?,
    val parent_id: Double?,
    val parent_level: Double?,
    val permissions: Any?,
    val phone: String?,
    val phone_verified_at: String?,
    val position: String?,
    val referral_link: String?,
    val region_id: Double?,
    val register_by: Double?,
    val right_children: Any?,
    val right_total: String?,
    val root_id: Any?,
    val status: Status?,
    val status_id: Double?,
    val step: Double?,
    val system_status: Double?,
    val user_avatar_path: String?,
    val uuid: String?,
    val who: String?
)

data class Child(
    val block_status: Double?,
    val childs: Double?,
    val city_id: Double?,
    val country_id: Double?,
    val created_at: String?,
    val direct_id: Double?,
    val direct_level: Double?,
    val document_back_path: String?,
    val document_front_path: String?,
    val first_name: String?,
    val id: Double?,
    val iin: String?,
    val is_active: Double?,
    val last_name: String?,
    val left_total: String?,
    val level: Double?,
    val middle_name: String?,
    val office_id: Double?,
    val package_id: Double?,
    val parent_id: Double?,
    val parent_level: Double?,
    val phone: String?,
    val phone_verified_at: String?,
    val position: String?,
    val referral_link: String?,
    val region_id: Double?,
    val register_by: Double?,
    val right_total: String?,
    val status_id: Double?,
    val step: Double?,
    val system_status: Double?,
    val status: Status?,
    val uuid: String?,
    val wallet_main_wallet: Double?
)

data class User(
    val birthday: String?,
    val block_status: Double?,
    val childs: Int?,
    val city: City?,
    val city_id: Double?,
    val country: Country?,
    val country_id: Double?,
    val created_at: String?,
    val direct_id: Double?,
    val direct_level: Double?,
    val document_back_path: String?,
    val document_front_path: String?,
    val email: String?,
    val first_name: String?,
    val id: Int?,
    val iin: String?,
    val is_active: Double?,
    val last_login: Any?,
    val last_name: String?,
    val left_children: Any?,
    val left_total: String?,
    val level: Double?,
    val middle_name: Any?,
    val office_id: Double?,
    val package_id: Double?,
    val parent_id: Double?,
    val parent_level: Double?,
    val permissions: Any?,
    val phone: String?,
    val phone_verified_at: String?,
    val position: String?,
    val referral_link: String?,
    val referral_link_left: String?,
    val referral_link_right: String?,
    val region: Region?,
    val region_id: Double?,
    val register_by: Double?,
    val right_children: Any?,
    val right_total: String?,
    val root_id: Any?,
    val status: StatusX?,
    val status_id: Double?,
    val step: Double?,
    val system_status: Double?,
    val user_avatar_path: String?,
    val uuid: String?,
    val wallet: Wallet?,
    val week_bonus: WeekBonus?,
    val who: String?,
    val verifyuser: VerifyUser?
)

data class WeekBonus(
    val id: Long?,
    val user_id: Int?,
    val team_bonus: Double?,
    val matching_bonus: Double?,
    val created_at: String?,
    val updated_at: String?,
    val left_week: Double?,
    val right_week: Double?
)

data class VerifyUser(
    val bank: String?,
    val bank_other_name: String?,
    val comment: String?,
    val created_at: String?,
    val iban: String?,
    val id: Int?,
    val iin: String?,
    val ip: String?,
    val middle_name: String?,
    val name: String?,
    val social_status: String?,
    val status: Int?,
    val status_name: String?,
    val surname: String?,
    val type: Int?,
    val updated_at: String?,
    val user_id: Int?
) {
    companion object {
        const val STATUS_NOT_VERIFIED = 0
        const val STATUS_VERIFIED = 1
        const val STATUS_WAITING_VERIFICATION = 2
        const val STATUS_REJECTED = 3
    }
}

data class Status(
    val deleted_at: Any?,
    val development_bonus_awards: Any?,
    val development_bonus_limits_per_status: Double?,
    val id: Double?,
    val low_branch_amount: Double?,
    val matching_bonus_depth: String?,
    val matching_bonus_percent: Double?,
    val purchases_cashback: Double?,
    val purchases_cashback_depth: String?,
    val status_description: String?,
    val status_name: String?,
    val team_bonus_limits_per_period: Double?,
    val team_bonus_percent: Double?,
    val updated_at: String?
)

data class City(
    val city_code: Any?,
    val city_description: Any?,
    val city_name: String?,
    val country_id: Any?,
    val id: Double?,
    val region_id: Double?
)

data class Country(
    val country_code: String?,
    val country_name: String?,
    val currency_id: Double?,
    val id: Double?
)

data class Region(
    val country_id: Double?,
    val id: Double?,
    val region_code: Any?,
    val region_name: String?
)

data class StatusX(
    val deleted_at: Any?,
    val development_bonus_awards: Any?,
    val development_bonus_limits_per_status: Double?,
    val id: Double?,
    val low_branch_amount: Double?,
    val matching_bonus_depth: String?,
    val matching_bonus_percent: Double?,
    val purchases_cashback: Double?,
    val purchases_cashback_depth: String?,
    val status_description: String?,
    val status_name: String?,
    val team_bonus_limits_per_period: Double?,
    val team_bonus_percent: Double?,
    val updated_at: String?
)

data class Wallet(
    val created_at: String?,
    val deleted_at: Any?,
    val id: Double?,
    val left_branch_total: Double?,
    val main_wallet: Double?,
    val purchase_wallet: Double?,
    val registry_wallet: Double?,
    val right_branch_total: Double?,
    val updated_at: String?,
    val user_id: Double?
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
    val document_front_path: String?,
    val user_avatar_path: String?
) : Parcelable