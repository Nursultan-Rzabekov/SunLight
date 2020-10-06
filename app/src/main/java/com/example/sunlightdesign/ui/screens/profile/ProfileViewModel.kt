package com.example.sunlightdesign.ui.screens.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.CountriesList
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.User
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.UsersList
import com.example.sunlightdesign.ui.base.StrongViewModel
import com.example.sunlightdesign.usecase.usercase.accountUse.GetAccountCountriesUseCase
import com.example.sunlightdesign.usecase.usercase.accountUse.GetAccountUsersListUseCase
import com.example.sunlightdesign.utils.Constants.Companion.ACTION_IMAGE_CONTENT_INTENT_CODE
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

    private var _rearDocument = MutableLiveData<Uri?>()
    val rearDocument: LiveData<Uri?> get() = _rearDocument

    private var _backDocument = MutableLiveData<Uri?>()
    val backDocument: LiveData<Uri?> get() = _backDocument

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
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            it.startActivityForResult(intent, ACTION_IMAGE_CONTENT_INTENT_CODE)
        }
    }

    fun onRearDocumentInvalidate() {
        _rearDocument.postValue(null)
    }

    fun onBackDocumentInvalidate() {
        _backDocument.postValue(null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && data != null){
            when(requestCode) {
                ACTION_IMAGE_CONTENT_INTENT_CODE -> {
                    Timber.d("Image path: ${data.data}")
                    if (_rearDocument.value != null) _backDocument.postValue(data.data)
                    else _rearDocument.postValue(data.data)
                }
            }
        }
    }
}


