package com.example.sunlightdesign.ui.screens.order

import androidx.lifecycle.MutableLiveData
import com.example.sunlightdesign.ui.base.StrongViewModel
import com.example.sunlightdesign.usecase.usercase.orders.get.GetOrderByIdUseCase
import com.example.sunlightdesign.usecase.usercase.orders.get.GetOrdersUseCase
import com.example.sunlightdesign.usecase.usercase.orders.get.GetProductByIdUseCase
import com.example.sunlightdesign.usecase.usercase.orders.get.GetProductListUseCase


/**
 * ViewModel for the task list screen.
 */
class OrderViewModel constructor(
    private val getOrdersUseCase: GetOrdersUseCase,
    private val getProductListUseCase: GetProductListUseCase,
    private val getOrderByIdUseCase: GetOrderByIdUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase
) : StrongViewModel() {

    var progress = MutableLiveData<Boolean>(false)

    fun getMyOrders() {
        progress.postValue(true)
        getOrdersUseCase.execute {
            onComplete {

            }
            onNetworkError {

            }
            onError {

            }
        }
    }

    fun getProductList() {
        progress.postValue(true)
        getProductListUseCase.execute {
            onComplete {
                progress.postValue(false)
            }
            onNetworkError {
                progress.postValue(false)
            }
            onError {
                progress.postValue(false)
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
            }
            onError {
                progress.postValue(false)
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
            }
            onError {
                progress.postValue(false)
            }
        }
    }


}


