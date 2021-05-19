package com.corp.sunlightdesign.koin

import com.corp.sunlightdesign.data.source.OrdersRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.orders.OrdersServices
import com.corp.sunlightdesign.data.source.repositories.DefaultOrdersRepository
import com.corp.sunlightdesign.ui.screens.order.OrderViewModel
import com.corp.sunlightdesign.usecase.usercase.orders.CalculateDeliveryUseCase
import com.corp.sunlightdesign.usecase.usercase.orders.get.*
import com.corp.sunlightdesign.usecase.usercase.orders.post.StoreDeliveryUseCase
import com.corp.sunlightdesign.usecase.usercase.orders.post.StoreOrderUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val ordersModule = module {

    single(named("ordersServices")) {
        get<Retrofit>().create(OrdersServices::class.java)
    }

    single<OrdersRepository> {
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

    factory {
        StoreOrderUseCase(
            ordersRepository = get()
        )
    }

    factory {
        GetOfficesListUseCase(
            ordersRepository = get()
        )
    }

    factory {
        StoreDeliveryUseCase(
            repository = get()
        )
    }

    factory {
        CalculateDeliveryUseCase(
            repository = get()
        )
    }

    viewModel {
        OrderViewModel(
            sharedUseCase = get(),
            getOrdersUseCase = get(),
            getOrderByIdUseCase = get(),
            getProductByIdUseCase = get(),
            getProductListUseCase = get(),
            storeOrderUseCase = get(),
            getOfficesListUseCase = get(),
            storeDeliveryUseCase = get(),
            accountCountriesUseCase = get(),
            profileInfoUseCase = get(),
            calculateDeliveryUseCase = get()
        )
    }
}
