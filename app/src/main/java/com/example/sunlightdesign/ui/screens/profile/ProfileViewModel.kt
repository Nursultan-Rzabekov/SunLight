package com.example.sunlightdesign.ui.screens.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.CountriesList
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.PackagesList
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.UsersList
import com.example.sunlightdesign.ui.base.StrongViewModel
import com.example.sunlightdesign.usecase.usercase.accountUse.get.AccountCountriesUseCase
import com.example.sunlightdesign.usecase.usercase.accountUse.get.AccountOfficesListUseCase
import com.example.sunlightdesign.usecase.usercase.accountUse.get.AccountPackagesListUseCase
import com.example.sunlightdesign.usecase.usercase.accountUse.get.AccountUsersListUseCase
import com.example.sunlightdesign.usecase.usercase.accountUse.post.AccountAddPartnerUseCase
import com.example.sunlightdesign.usecase.usercase.accountUse.post.AccountCreateOrderUseCase
import com.example.sunlightdesign.usecase.usercase.accountUse.post.AccountSetPackagesUseCase


/**
 * ViewModel for the task list screen.
 */
class ProfileViewModel  constructor(
    private val accountCountriesUseCase: AccountCountriesUseCase,
    private val accountUsersListUseCase: AccountUsersListUseCase,
    private val accountPackagesListUseCase: AccountPackagesListUseCase,
    private val accountOfficesListUseCase: AccountOfficesListUseCase,
    private val accountSetPackagesUseCase: AccountSetPackagesUseCase,
    private val accountAddPartnerUseCase: AccountAddPartnerUseCase,
    private val accountCreateOrderUseCase: AccountCreateOrderUseCase

) : StrongViewModel() {

    var progress = MutableLiveData<Boolean>(false)

    private var _countriesList = MutableLiveData<CountriesList>()
    val countriesList: LiveData<CountriesList> get() = _countriesList

    private var _usersList = MutableLiveData<UsersList>()
    val usersList: LiveData<UsersList> get() = _usersList

    private var _packageList = MutableLiveData<PackagesList>()
    val packageList: LiveData<PackagesList> get() = _packageList


    fun getCountriesList(){
        progress.postValue(true)
        accountCountriesUseCase.execute {
            onComplete {
                progress.postValue(false)
                _countriesList.postValue(it)
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

    fun getUsersList(){
        progress.postValue(true)
        accountUsersListUseCase.execute {
            onComplete {
                progress.postValue(false)
                _usersList.postValue(it)
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

    fun getPackagesList(){
        progress.postValue(true)
        accountPackagesListUseCase.execute {
            onComplete {
                progress.postValue(false)
                _packageList.postValue(it)
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

    fun getOfficesList(){
        progress.postValue(true)
        accountOfficesListUseCase.execute {
            onComplete {  }
            onNetworkError {  }
            onError {  }
        }
    }

    fun createOrder(){
        progress.postValue(true)
        accountCreateOrderUseCase.execute {
            onComplete {  }
            onNetworkError {  }
            onError {  }
        }
    }

    fun setPackages(){
        progress.postValue(true)
        accountSetPackagesUseCase.execute {
            onComplete {  }
            onNetworkError {  }
            onError {  }
        }
    }




}


