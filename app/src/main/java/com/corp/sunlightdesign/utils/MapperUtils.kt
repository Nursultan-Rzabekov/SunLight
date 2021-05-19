package com.corp.sunlightdesign.utils

import com.corp.sunlightdesign.data.source.dataSource.remote.profile.entity.ShortenedUserInfo
import com.corp.sunlightdesign.data.source.dataSource.remote.profile.entity.UserInfo

fun UserInfo?.toShortenedUserInfo() = ShortenedUserInfo(
        birthday = this?.user?.birthday,
        countryName = this?.user?.country?.country_name,
        regionName = this?.user?.region?.region_name,
        cityName = this?.user?.city?.city_name,
        fullName = "${this?.user?.first_name} ${this?.user?.last_name}",
        document_back_path = this?.user?.document_back_path,
        document_front_path = this?.user?.document_front_path,
        status = this?.user?.status?.status_name,
        phone = this?.user?.phone,
        uuid = this?.user?.uuid,
        user_avatar_path = this?.user?.user_avatar_path
    )