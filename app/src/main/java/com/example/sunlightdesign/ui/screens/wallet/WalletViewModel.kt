package com.example.sunlightdesign.ui.screens.wallet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sunlightdesign.data.source.AuthRepository
import com.example.sunlightdesign.data.source.dataSource.remote.wallets.entity.CurrencyCalculate
import com.example.sunlightdesign.data.source.dataSource.remote.wallets.entity.CurrencyX
import com.example.sunlightdesign.data.source.dataSource.remote.wallets.entity.Wallet
import com.example.sunlightdesign.ui.base.StrongViewModel
import com.example.sunlightdesign.usecase.usercase.walletUse.WalletCalculateInfoUseCase
import com.example.sunlightdesign.usecase.usercase.walletUse.WalletInfoUseCase


/**
 * ViewModel for the task list screen.
 */
class WalletViewModel constructor(
    private val walletInfoUseCase: WalletInfoUseCase,
    private val walletCalculateInfoUseCase: WalletCalculateInfoUseCase
) : StrongViewModel() {

    var progress = MutableLiveData<Boolean>(false)

    private var _walletLiveData = MutableLiveData<Wallet>()
    val walletLiveData: LiveData<Wallet> get() = _walletLiveData

    private var _calculateInfo = MutableLiveData<CurrencyCalculate>()
    val calculateInfo: LiveData<CurrencyCalculate> get() = _calculateInfo

    private var _selectedCurrency = MutableLiveData<CurrencyX>()
    val selectedCurrency: LiveData<CurrencyX> get() = _selectedCurrency

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

    fun onSelectCurrency(currency: CurrencyX) {
        _selectedCurrency.postValue(currency)
    }

}


