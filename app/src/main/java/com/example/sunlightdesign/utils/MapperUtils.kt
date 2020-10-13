package com.example.sunlightdesign.utils

import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.ShortenedUserInfo
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.UserInfo

fun UserInfo?.toShortenedUserInfo(): ShortenedUserInfo {
    return ShortenedUserInfo(
        birthday = this?.user?.birthday,
        countryName = this?.user?.country?.country_name,
        regionName = this?.user?.region?.region_name,
        cityName = this?.user?.city?.city_name,
        fullName = "${this?.user?.last_name} ${this?.user?.last_name}",
        document_back_path = this?.user?.document_back_path,
        document_front_path = this?.user?.document_front_path,
        status = this?.user?.status?.status_name,
        phone = this?.user?.phone,
        uuid = this?.user?.uuid
    )
}