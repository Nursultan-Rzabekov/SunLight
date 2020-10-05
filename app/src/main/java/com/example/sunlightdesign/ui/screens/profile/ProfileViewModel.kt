package com.example.sunlightdesign.ui.screens.profile

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.CountriesList
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.User
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.UsersList
import com.example.sunlightdesign.ui.base.StrongViewModel
import com.example.sunlightdesign.usecase.usercase.accountUse.GetAccountCountriesUseCase
import com.example.sunlightdesign.usecase.usercase.accountUse.GetAccountUsersListUseCase
import timber.log.Timber


/**
 * ViewModel for the task list screen.
 */
class ProfileViewModel  constructor(
    private val getAccountCountriesUseCase: GetAccountCountriesUseCase,
    private val getAccountUsersListUseCase: GetAccountUsersListUseCase
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

    fun onAttachDocument() {
        withActivity{
            Timber.d("Attach Document")
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            it.startActivityForResult(intent, 5)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.d("--- onActivity Result ---")
        if(resultCode == Activity.RESULT_OK){
            when(requestCode) {
                5 -> {
                    Timber.d("Image path: ${data?.data}")
                }
            }
        }
    }
}


