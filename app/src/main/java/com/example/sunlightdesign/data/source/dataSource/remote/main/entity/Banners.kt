package com.example.sunlightdesign.data.source.dataSource.remote.main.entity

data class Banners(
    val banners: List<Banner>
)

data class Banner(
    val content: String,
    val description: String,
    val id: Int,
    val link: String,
    val media_caption: Any,
    val media_path: String,
    val media_path_mobile: String,
    val sort: Int,
    val status: Int,
    val title: String,
    val type: Int,
    val post_id: Int
)