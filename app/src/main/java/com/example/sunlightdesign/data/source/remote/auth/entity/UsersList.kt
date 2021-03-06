package com.example.sunlightdesign.data.source.remote.auth.entity

data class UsersList(
    val message: String?,
    val users: List<Users>?
)

data class Users(
    val first_name: String?,
    val id: Int?,
    val iin: String?,
    val last_name: String?,
    val middle_name: String?
)