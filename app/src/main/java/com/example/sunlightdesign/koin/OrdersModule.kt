package com.example.sunlightdesign.koin

import com.example.sunlightdesign.data.source.OrdersRepository
import com.example.sunlightdesign.data.source.dataSource.remote.orders.OrdersServices
import com.example.sunlightdesign.data.source.repositories.DefaultOrdersRepository
import com.example.sunlightdesign.ui.screens.order.OrderViewModel
import com.example.sunlightdesign.usecase.usercase.orders.get.GetOrderByIdUseCase
import com.example.sunlightdesign.usecase.usercase.orders.get.GetOrdersUseCase
import com.example.sunlightdesign.usecase.usercase.orders.get.GetProductByIdUseCase
import com.example.sunlightdesign.usecase.usercase.orders.get.GetProductListUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val ordersModule = module {

    single(named("ordersServices")){
        get<Retrofit>().create(OrdersServices::class.java)
    }

    single<OrdersRepository>{
        DefaultOrdersRepository(
            ordersServices = get(named("ordersServices"))
        )
    }

    factory {
        GetOrderByIdUseCase(
            ordersRepository = get()
        )
    }

    factory {
        GetProductByIdUseCase(
            ordersRepository = get()
        )
    }

    factory {
        GetProductListUseCase(
            ordersRepository = get()
        )
    }

    factory {
        GetOrdersUseCase(
            ordersRepository = get()
        )
    }

    viewModel {
        OrderViewModel(
            getOrdersUseCase = get(),
            getOrderByIdUseCase = get(),
            getProductByIdUseCase = get(),
            getProductListUseCase = get()
        )
    }
}