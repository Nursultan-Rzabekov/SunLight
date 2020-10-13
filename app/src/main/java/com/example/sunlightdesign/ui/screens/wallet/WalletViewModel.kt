package com.example.sunlightdesign.ui.screens.wallet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sunlightdesign.data.source.AuthRepository
import com.example.sunlightdesign.data.source.dataSource.remote.wallets.entity.Wallet
import com.example.sunlightdesign.ui.base.StrongViewModel
import com.example.sunlightdesign.usecase.usercase.walletUse.WalletInfoUseCase


/**
 * ViewModel for the task list screen.
 */
class WalletViewModel constructor(
    private val walletInfoUseCase: WalletInfoUseCase
) : StrongViewModel() {

    var progress = MutableLiveData<Boolean>(false)

    private var _walletLiveData = MutableLiveData<Wallet>()
    val walletLiveData: LiveData<Wallet> get() = _walletLiveData

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

}


