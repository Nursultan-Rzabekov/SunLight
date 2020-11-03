package com.example.sunlightdesign.ui.screens.wallet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.OfficesList
import com.example.sunlightdesign.data.source.dataSource.remote.wallets.entity.CurrencyCalculate
import com.example.sunlightdesign.data.source.dataSource.remote.wallets.entity.CurrencyX
import com.example.sunlightdesign.data.source.dataSource.remote.wallets.entity.Wallet
import com.example.sunlightdesign.ui.base.StrongViewModel
import com.example.sunlightdesign.usecase.usercase.walletUse.get.WalletCalculateInfoUseCase
import com.example.sunlightdesign.usecase.usercase.walletUse.get.WalletGetOfficesUseCase
import com.example.sunlightdesign.usecase.usercase.walletUse.get.WalletInfoUseCase


/**
 * ViewModel for the task list screen.
 */
class WalletViewModel constructor(
    private val walletInfoUseCase: WalletInfoUseCase,
    private val walletCalculateInfoUseCase: WalletCalculateInfoUseCase,
    private val walletGetOfficesUseCase: WalletGetOfficesUseCase
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

}


