package com.example.sunlightdesign.data.source.dataSource.remote.main.entity

data class CompanyInfo(
    val page: Page
)

data class Page(
    val content: String,
    val created_at: String,
    val id: Int,
    val page_name: String,
    val page_url: String,
    val updated_at: String
)