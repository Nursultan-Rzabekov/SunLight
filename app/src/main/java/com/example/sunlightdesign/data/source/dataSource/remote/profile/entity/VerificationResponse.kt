package com.example.sunlightdesign.data.source.dataSource.remote.profile.entity

data class VerificationResponse(
    val verify: Verify?,
    val verify_images: List<VerifyImage>?
)

data class Verify(
    val bank: BankName?,
    val bank_other_name: String?,
    val comment: Any?,
    val created_at: String?,
    val iban: String?,
    val id: Int?,
    val iin: String?,
    val ip: String?,
    val middle_name: String?,
    val name: String?,
    val social_status: List<SocialStatusArr>?,
    val status: Int?,
    val status_name: String?,
    val surname: String?,
    val type: Int?,
    val updated_at: String?,
    val user_id: Int?
)

data class VerifyImage(
    val created_at: String?,
    val id: Int?,
    val updated_at: String?,
    val verify_id: String?,
    val verify_path: String?
)