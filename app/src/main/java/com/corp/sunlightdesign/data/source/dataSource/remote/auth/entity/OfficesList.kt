package com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity

data class OfficesList(
    val message: String?,
    val offices: List<Office?>?
)

data class Office(
    val address: String?,
    val city_id: Int?,
    val close_hours: String?,
    val country_id: Int?,
    val created_at: String?,
    val deleted_at: String?,
    val id: Int?,
    val latitude: Any?,
    val longitude: Any?,
    val office_image_path: String?,
    val office_name: String?,
    val open_hours: String?,
    val phone: String?,
    val region_id: Int?,
    val updated_at: String?,
    val city: ShortenedCityInfo?,
    var isChecked: Boolean = false
)

data class ShortenedCityInfo(
    val id: Int?,
    val city_name: String?
)