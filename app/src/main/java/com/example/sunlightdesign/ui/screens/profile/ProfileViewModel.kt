package com.example.sunlightdesign.ui.screens.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.*
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.UserInfo
import com.example.sunlightdesign.ui.base.StrongViewModel
import com.example.sunlightdesign.usecase.usercase.accountUse.get.AccountCountriesUseCase
import com.example.sunlightdesign.usecase.usercase.accountUse.get.AccountOfficesListUseCase
import com.example.sunlightdesign.usecase.usercase.accountUse.get.AccountPackagesListUseCase
import com.example.sunlightdesign.usecase.usercase.accountUse.get.AccountUsersListUseCase
import com.example.sunlightdesign.usecase.usercase.accountUse.post.AccountAddPartnerUseCase
import com.example.sunlightdesign.usecase.usercase.accountUse.post.AccountCreateOrderUseCase
import com.example.sunlightdesign.usecase.usercase.accountUse.post.AccountSetPackagesUseCase
import com.example.sunlightdesign.usecase.usercase.profileUse.ProfileInfoUseCase
import com.example.sunlightdesign.utils.Constants
import timber.log.Timber


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
    private val accountCreateOrderUseCase: AccountCreateOrderUseCase,
    private val profileInfoUseCase: ProfileInfoUseCase
) : StrongViewModel() {

    var progress = MutableLiveData<Boolean>(false)

    private var _countriesList = MutableLiveData<CountriesList>()
    val countriesList: LiveData<CountriesList> get() = _countriesList

    private var _usersList = MutableLiveData<UsersList>()
    val usersList: LiveData<UsersList> get() = _usersList

    private var _packageList = MutableLiveData<PackagesList>()
    val packageList: LiveData<PackagesList> get() = _packageList

    private var _backDocument = MutableLiveData<Uri?>()
    val backDocument: LiveData<Uri?> get() = _backDocument

    private var _rearDocument = MutableLiveData<Uri?>()
    val rearDocument: LiveData<Uri?> get() = _rearDocument

    private var _avatarImage = MutableLiveData<Uri?>()
    val avatarImage: LiveData<Uri?> get() = _avatarImage

    private var _productsList = MutableLiveData<List<Product>?>()
    val productsList: LiveData<List<Product>?> get() = _productsList

    private var _officesList = MutableLiveData<OfficesList>()
    val officeList: LiveData<OfficesList> get() = _officesList

    private var _profileInfo = MutableLiveData<UserInfo>()
    val profileInfo: LiveData<UserInfo> get() = _profileInfo

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

    fun getProfileInfo(){
        progress.postValue(true)
        profileInfoUseCase.execute {
            onComplete {
                progress.postValue(false)
                _profileInfo.postValue(it)
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


    fun onAttachDocument(requestCode: Int = Constants.ACTION_IMAGE_CONTENT_INTENT_CODE) {
        withActivity{
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            it.startActivityForResult(intent, requestCode)
        }
    }

    fun onRearDocumentInvalidate() {
        _rearDocument.postValue(null)
    }

    fun onBackDocumentInvalidate() {
        _backDocument.postValue(null)
    }

    fun onPackageSelected(index: Int) {
        _productsList.postValue(
            _packageList.value?.packages?.get(index)?.products
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && data != null){
            when(requestCode) {
                Constants.ACTION_IMAGE_CONTENT_INTENT_CODE -> {
                    Timber.d("Image path: ${data.data}")
                    if (_rearDocument.value != null) _backDocument.postValue(data.data)
                    else _rearDocument.postValue(data.data)
                }
                Constants.ACTION_IMAGE_CONTENT_AVATAR_CODE -> {
                    Timber.d("Image path: ${data.data}")
                    _avatarImage.postValue(data.data)
                }
            }
        }
    }
}


