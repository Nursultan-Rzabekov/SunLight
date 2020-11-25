package com.example.sunlightdesign.ui.screens.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sunlightdesign.data.source.dataSource.CreateOrderPartner
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.OfficesList
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.OrderProducts
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.Orders
import com.example.sunlightdesign.ui.base.StrongViewModel
import com.example.sunlightdesign.usecase.usercase.SharedUseCase
import com.example.sunlightdesign.usecase.usercase.orders.get.*
import com.example.sunlightdesign.usecase.usercase.orders.post.StoreOrderUseCase


/**
 * ViewModel for the task list screen.
 */
class OrderViewModel constructor(
    private val sharedUseCase: SharedUseCase,
    private val getOrdersUseCase: GetOrdersUseCase,
    private val getProductListUseCase: GetProductListUseCase,
    private val getOrderByIdUseCase: GetOrderByIdUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val storeOrderUseCase: StoreOrderUseCase,
    private val getOfficesListUseCase: GetOfficesListUseCase
) : StrongViewModel() {

    var progress = MutableLiveData<Boolean>(false)

    var createOrderBuilder: CreateOrderPartner.Builder = CreateOrderPartner.Builder()

    private var _orders = MutableLiveData<Orders>()
    val orders: LiveData<Orders> get() = _orders

    private var _products = MutableLiveData<OrderProducts>()
    val products: LiveData<OrderProducts> get() = _products

    private var _orderState = MutableLiveData<Boolean>(false)
    val orderState: LiveData<Boolean> get() = _orderState

    private var _officesList = MutableLiveData<OfficesList>()
    val officesList: LiveData<OfficesList> get() = _officesList

    fun getUserId() = sharedUseCase.getSharedPreference().userId

    fun getMyOrders() {
        progress.postValue(true)
        getOrdersUseCase.execute {
            onComplete {
                progress.postValue(false)
                _orders.postValue(it)
            }
            onNetworkError {
                progress.postValue(false)
                handleError(errorMessage = it.message)
            }
            onError {
                progress.postValue(false)
                handleError(throwable = it)
            }
        }
    }

    fun getProductList() {
        progress.postValue(true)
        getProductListUseCase.execute {
            onComplete {
                progress.postValue(false)
                _products.postValue(it)
            }
            onNetworkError {
                progress.postValue(false)
                handleError(errorMessage = it.message)
            }
            onError {
                progress.postValue(false)
                handleError(throwable = it)
            }
        }
    }

    fun getOrderByID() {
        progress.postValue(true)
        getOrderByIdUseCase.execute {
            onComplete {
                progress.postValue(false)
            }
            onNetworkError {
                progress.postValue(false)
                handleError(errorMessage = it.message)
            }
            onError {
                progress.postValue(false)
                handleError(throwable = it)
            }
        }
    }

    fun getProductByID() {
        progress.postValue(true)
        getProductByIdUseCase.execute {
            onComplete {
                progress.postValue(false)
            }
            onNetworkError {
                progress.postValue(false)
                handleError(errorMessage = it.message)
            }
            onError {
                progress.postValue(false)
                handleError(throwable = it)
            }
        }
    }


    fun storeOrder(createOrderPartner: CreateOrderPartner){
        progress.postValue(true)
        storeOrderUseCase.setData(createOrderPartner)

        storeOrderUseCase.execute {
            onComplete {
                progress.postValue(false)
                _orderState.postValue(true)
            }
            onNetworkError {
                progress.postValue(false)
                handleError(errorMessage = it.message)
                _orderState.postValue(false)
            }
            onError {
                progress.postValue(false)
                handleError(throwable = it)
                _orderState.postValue(false)
            }
        }
    }


    fun getOfficesList(){
        progress.postValue(true)
        getOfficesListUseCase.execute {
            onComplete {
                progress.postValue(false)
                _officesList.postValue(it)
            }
            onNetworkError {
                progress.postValue(false)
                handleError(errorMessage = it.message)
            }
            onError {
                progress.postValue(false)
                handleError(throwable = it)
            }
        }
    }


}


