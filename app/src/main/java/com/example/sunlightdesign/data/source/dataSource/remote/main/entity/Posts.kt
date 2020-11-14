package com.example.sunlightdesign.data.source.dataSource.remote.main.entity

data class Posts(
    val posts: List<Post>
)

data class Post(
    val content: String,
    val created_at: String,
    val description: String,
    val id: Int,
    val image: String,
    val title: String,
    val updated_at: String
)