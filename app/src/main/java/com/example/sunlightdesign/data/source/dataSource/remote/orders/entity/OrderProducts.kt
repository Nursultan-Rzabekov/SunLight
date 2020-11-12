package com.example.sunlightdesign.data.source.dataSource.remote.orders.entity

import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Product

data class OrderProducts(
    val products: List<Product>?
)