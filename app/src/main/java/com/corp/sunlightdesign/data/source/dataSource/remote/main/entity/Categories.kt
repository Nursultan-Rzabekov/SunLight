package com.corp.sunlightdesign.data.source.dataSource.remote.main.entity

data class Categories(
    val categories: List<Category>
)

data class Category(
    val created_at: String,
    val deleted_at: Any,
    val id: Int,
    val name: String,
    val slug: String,
    val updated_at: String,
    var isSelected: Boolean
)