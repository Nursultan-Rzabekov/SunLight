package com.example.sunlightdesign.ui.launcher.company

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sunlightdesign.ui.base.StrongViewModel
import com.example.sunlightdesign.usecase.usercase.authUse.GetLoginAuthUseCase
import com.example.sunlightdesign.usecase.usercase.mainUse.get.GetСompanyInfoUseCase


class CompanyViewModel constructor(
    private val getCompanyInfoUseCase: GetСompanyInfoUseCase
) : StrongViewModel() {

    var progress = MutableLiveData<Boolean>(false)

    private var _contentMarket = MutableLiveData<String>()
    val contentMarket: LiveData<String> get() = _contentMarket

    private var _contentContacts = MutableLiveData<String>()
    val contentContacts: LiveData<String> get() = _contentContacts

    fun getMarketInfoUseCase(){
        progress.postValue(true)
        getCompanyInfoUseCase.setData("marketing-plan")

        getCompanyInfoUseCase.execute {
            onComplete {
                progress.postValue(false)
                _contentMarket.postValue(it.page.content)
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

    fun getContactInfoUseCase(){
        progress.postValue(true)
        getCompanyInfoUseCase.setData("contacts")

        getCompanyInfoUseCase.execute {
            onComplete {
                progress.postValue(false)
                _contentContacts.postValue(it.page.content)
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


