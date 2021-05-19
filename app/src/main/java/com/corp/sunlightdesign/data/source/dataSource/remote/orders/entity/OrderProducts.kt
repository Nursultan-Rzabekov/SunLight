package com.corp.sunlightdesign.data.source.dataSource.remote.orders.entity

import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.Product
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.Wallet

data class OrderProducts(
    val products: List<Product>?,
    val wallet: Wallet?
)