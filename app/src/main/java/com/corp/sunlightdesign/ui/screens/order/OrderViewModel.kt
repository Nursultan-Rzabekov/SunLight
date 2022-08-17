package com.corp.sunlightdesign.ui.screens.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.corp.sunlightdesign.data.source.dataSource.CreateEvent
import com.corp.sunlightdesign.data.source.dataSource.CreateOrderPartner
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.CountriesList
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.OfficesList
import com.corp.sunlightdesign.data.source.dataSource.remote.orders.entity.*
import com.corp.sunlightdesign.data.source.dataSource.remote.profile.entity.UserInfo
import com.corp.sunlightdesign.ui.base.StrongViewModel
import com.corp.sunlightdesign.usecase.usercase.SharedUseCase
import com.corp.sunlightdesign.usecase.usercase.accountUse.get.AccountCountriesUseCase
import com.corp.sunlightdesign.usecase.usercase.orders.CalculateDeliveryUseCase
import com.corp.sunlightdesign.usecase.usercase.orders.get.*
import com.corp.sunlightdesign.usecase.usercase.orders.post.BuyEventUseCase
import com.corp.sunlightdesign.usecase.usercase.orders.post.StoreDeliveryUseCase
import com.corp.sunlightdesign.usecase.usercase.orders.post.StoreOrderUseCase
import com.corp.sunlightdesign.usecase.usercase.profileUse.get.ProfileInfoUseCase
import com.corp.sunlightdesign.utils.ErrorListException
import timber.log.Timber


/**
 * ViewModel for the task list screen.
 */
class OrderViewModel constructor(
    private val sharedUseCase: SharedUseCase,
    private val getOrdersUseCase: GetOrdersUseCase,
    private val getProductListUseCase: GetProductListUseCase,
    private val getEventListUseCase: GetEventListUseCase,
    private val getOrderByIdUseCase: GetOrderByIdUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val storeOrderUseCase: StoreOrderUseCase,
    private val createEventUseCase: BuyEventUseCase,
    private val getOfficesListUseCase: GetOfficesListUseCase,
    private val storeDeliveryUseCase: StoreDeliveryUseCase,
    private val accountCountriesUseCase: AccountCountriesUseCase,
    private val profileInfoUseCase: ProfileInfoUseCase,
    private val calculateDeliveryUseCase: CalculateDeliveryUseCase,
    private val getMyTicketsUseCase: GetMyTicketsUseCase
) : StrongViewModel() {

    var progress = MutableLiveData<Boolean>(false)

    var createOrderBuilder: CreateOrderPartner.Builder = CreateOrderPartner.Builder()

    var createEvent: CreateEvent.Builder = CreateEvent.Builder()

    private var _orders = MutableLiveData<Orders>()
    val orders: LiveData<Orders> get() = _orders

    private var _tickets = MutableLiveData<MyTickets>()
    val tickets: LiveData<MyTickets> get() = _tickets

    private var _products = MutableLiveData<OrderProducts>()
    val products: LiveData<OrderProducts> get() = _products

    private var _events = MutableLiveData<OrderEvents>()
    val events: LiveData<OrderEvents> get() = _events

    private var _orderState = MutableLiveData<OrderShortResponse>()
    val orderState: LiveData<OrderShortResponse> get() = _orderState

    private var _eventState = MutableLiveData<EventShortResponse>()
    val eventState: LiveData<EventShortResponse> get() = _eventState

    private var _officesList = MutableLiveData<OfficesList>()
    val officesList: LiveData<OfficesList> get() = _officesList

    private var _deliverResponse = MutableLiveData<DeliverResponse>()
    val deliverResponse: LiveData<DeliverResponse> get() = _deliverResponse

    private var _locationsList = MutableLiveData<CountriesList>()
    val locationList: LiveData<CountriesList> get() = _locationsList

    private var _userInfo = MutableLiveData<UserInfo>()
    val userInfo: LiveData<UserInfo> get() = _userInfo

    private var _deliveryService = MutableLiveData<DeliveryServiceListResponse>()
    val deliveryService: LiveData<DeliveryServiceListResponse> get() = _deliveryService

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

    fun getMyTickets() {
        progress.postValue(true)
        getMyTicketsUseCase.execute {
            onComplete {
                progress.postValue(false)
                _tickets.postValue(it)
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

    fun getEventList() {
        progress.postValue(true)

        getEventListUseCase.execute {
            onComplete {
                progress.postValue(false)
                _events.postValue(it)
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


    fun storeOrder(createOrderPartner: CreateOrderPartner) {
        progress.postValue(true)
        storeOrderUseCase.setData(createOrderPartner)
        storeOrderUseCase.execute {
            onComplete {
                progress.postValue(false)
                Timber.d(it?.orders.toString())
                _orderState.postValue(
                    OrderShortResponse(
                        orderType = it?.order?.order_payment_type ?: -1,
                        isSuccess = true
                    )
                )
            }
            onNetworkError {
                progress.postValue(false)
                handleError(errorMessage = it.message)
                _orderState.postValue(
                    OrderShortResponse(
                        orderType = -1,
                        isSuccess = false
                    )
                )
            }
            onError {
                progress.postValue(false)
                handleError(throwable = it)
                _orderState.postValue(
                    OrderShortResponse(
                        orderType = -1,
                        isSuccess = false
                    )
                )
            }
        }
    }


    fun buyEvent(createEvent: CreateEvent) {
        progress.postValue(true)
        createEventUseCase.setData(createEvent)
        createEventUseCase.execute {
            onComplete {
                progress.postValue(false)
                _eventState.postValue(
                    EventShortResponse(
                        isSuccess = true,
                        message = "Детали заказа вы можете увидеть\nв “Мои заказы“ "
                    )
                )

            }
            onNetworkError {
                progress.postValue(false)
                _eventState.postValue(
                    EventShortResponse(
                        isSuccess = false,
                        message = it.message.toString()
                    )
                )
            }
            onError {
                progress.postValue(false)
                when (it) {
                    is ErrorListException -> {
                        _eventState.postValue(
                            EventShortResponse(
                                isSuccess = false,
                                message = it.error.toString()
                            )
                        )
                    }
                    else -> handleError(throwable = it)
                }
            }
        }
    }


    fun getOfficesList() {
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

    fun storeDelivery(delivery: StoreDeliveryUseCase.DeliverRequest) {
        progress.postValue(true)
        storeDeliveryUseCase.setModel(delivery)
        storeDeliveryUseCase.execute {
            onComplete {
                progress.postValue(false)
                _deliverResponse.postValue(it)
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

    fun getUserInfo() {
        progress.postValue(true)
        profileInfoUseCase.execute {
            onComplete {
                progress.postValue(false)
                _userInfo.postValue(it)
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

    fun getLocations() {
        progress.postValue(true)
        accountCountriesUseCase.execute {
            onComplete {
                progress.postValue(false)
                _locationsList.postValue(it)
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

    fun calculateDelivery(request: CalculateDeliveryUseCase.Request) {
        progress.postValue(true)
        calculateDeliveryUseCase.setModel(request)
        calculateDeliveryUseCase.execute {
            onComplete {
                progress.postValue(false)
                _deliveryService.postValue(it)
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

    data class OrderShortResponse(
        val orderType: Int,
        val isSuccess: Boolean
    )

    data class EventShortResponse(
        val isSuccess: Boolean,
        val message: String
    )
}

