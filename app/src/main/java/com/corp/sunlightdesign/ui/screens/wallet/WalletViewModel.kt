package com.corp.sunlightdesign.ui.screens.wallet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.OfficesList
import com.corp.sunlightdesign.data.source.dataSource.remote.wallets.entity.WithdrawalReceipt
import com.corp.sunlightdesign.data.source.dataSource.remote.wallets.entity.CurrencyCalculate
import com.corp.sunlightdesign.data.source.dataSource.remote.wallets.entity.CurrencyX
import com.corp.sunlightdesign.data.source.dataSource.remote.wallets.entity.Wallet
import com.corp.sunlightdesign.ui.base.StrongViewModel
import com.corp.sunlightdesign.usecase.usercase.walletUse.get.WalletCalculateInfoUseCase
import com.corp.sunlightdesign.usecase.usercase.walletUse.get.WalletGetOfficesUseCase
import com.corp.sunlightdesign.usecase.usercase.walletUse.get.WalletInfoUseCase
import com.corp.sunlightdesign.usecase.usercase.walletUse.post.SetWithdrawal
import com.corp.sunlightdesign.usecase.usercase.walletUse.post.WalletStoreWithdrawalUseCase
import com.corp.sunlightdesign.utils.ErrorListException


/**
 * ViewModel for the task list screen.
 */
class WalletViewModel constructor(
    private val walletInfoUseCase: WalletInfoUseCase,
    private val walletCalculateInfoUseCase: WalletCalculateInfoUseCase,
    private val walletGetOfficesUseCase: WalletGetOfficesUseCase,
    private val walletStoreWithdrawalUseCase: WalletStoreWithdrawalUseCase
) : StrongViewModel() {

    var progress = MutableLiveData<Boolean>(false)

    private var _walletLiveData = MutableLiveData<Wallet>()
    val walletLiveData: LiveData<Wallet> get() = _walletLiveData

    private var _calculateInfo = MutableLiveData<CurrencyCalculate>()
    val calculateInfo: LiveData<CurrencyCalculate> get() = _calculateInfo

    private var _selectedCurrency = MutableLiveData<CurrencyX>()
    val selectedCurrency: LiveData<CurrencyX> get() = _selectedCurrency

    private var _officesList = MutableLiveData<OfficesList>()
    val officesList: LiveData<OfficesList> get() = _officesList

    private var _withdrawReceipt = MutableLiveData<WithdrawalReceipt>()
    val withdrawReceipt: LiveData<WithdrawalReceipt> get() = _withdrawReceipt

    fun getWalletInfo(){
        progress.postValue(true)
        walletInfoUseCase.execute {
            onComplete {
                progress.postValue(false)
                _walletLiveData.postValue(it)
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

    fun getCurrencyInfo(){
        progress.postValue(true)
        walletCalculateInfoUseCase.execute {
            onComplete {
                progress.postValue(false)
                _calculateInfo.postValue(it)
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

    fun getOffices() {
        progress.postValue(true)
        walletGetOfficesUseCase.execute {
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

    fun onSelectCurrency(currency: CurrencyX) {
        _selectedCurrency.postValue(currency)
    }

    fun storeWithdraw(setWithdrawal: SetWithdrawal) {
        progress.postValue(true)
        walletStoreWithdrawalUseCase.setData(setWithdrawal)
        walletStoreWithdrawalUseCase.execute {
            onComplete {
                progress.postValue(false)
                _withdrawReceipt.postValue(it)
            }
            onNetworkError {
                progress.postValue(false)
                handleError(errorMessage = it.message)
            }
            onError {
                progress.postValue(false)
                when (it) {
                    is ErrorListException -> {
                        val firstMessage = it.errorMap?.values?.firstOrNull()?.firstOrNull()
                        handleError(errorMessage = firstMessage.toString())
                    }
                    else -> handleError(throwable = it)
                }
            }
        }
    }

    data class ShortenedCity (
        val city_id: Int,
        val city_name: String
    ) {
        override fun toString(): String = city_name
    }


    fun clean() {
        _withdrawReceipt = MutableLiveData()
        _officesList = MutableLiveData()
        _calculateInfo = MutableLiveData()
        _selectedCurrency = MutableLiveData()
        _walletLiveData = MutableLiveData()
    }
}


