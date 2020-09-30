package com.example.sunlightdesign.ui.screens.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.CountriesList
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.UsersList
import com.example.sunlightdesign.ui.base.StrongViewModel
import com.example.sunlightdesign.usecase.usercase.accountUse.get.AccountCountriesUseCase
import com.example.sunlightdesign.usecase.usercase.accountUse.get.AccountUsersListUseCase


/**
 * ViewModel for the task list screen.
 */
class ProfileViewModel  constructor(
    private val getAccountCountriesUseCase: AccountCountriesUseCase,
    private val getAccountUsersListUseCase: AccountUsersListUseCase
) : StrongViewModel() {

    var progress = MutableLiveData<Boolean>(false)

    private var _countriesList = MutableLiveData<CountriesList>()
    val countriesList: LiveData<CountriesList> get() = _countriesList

    private var _usersList = MutableLiveData<UsersList>()
    val usersList: LiveData<UsersList> get() = _usersList

    fun getCountriesList(){
        progress.postValue(true)
        getAccountCountriesUseCase.execute {
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
        getAccountUsersListUseCase.execute {
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


}


