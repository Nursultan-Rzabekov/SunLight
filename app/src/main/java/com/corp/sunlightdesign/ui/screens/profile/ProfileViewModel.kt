package com.corp.sunlightdesign.ui.screens.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.data.source.dataSource.AddPartner
import com.corp.sunlightdesign.data.source.dataSource.CreateOrderPartner
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.*
import com.corp.sunlightdesign.data.source.dataSource.remote.orders.entity.DeliveryServiceListResponse
import com.corp.sunlightdesign.data.source.dataSource.remote.profile.entity.InvitedResponse
import com.corp.sunlightdesign.data.source.dataSource.remote.profile.entity.UserInfo
import com.corp.sunlightdesign.ui.base.StrongViewModel
import com.corp.sunlightdesign.usecase.usercase.SharedUseCase
import com.corp.sunlightdesign.usecase.usercase.accountUse.get.AccountCountriesUseCase
import com.corp.sunlightdesign.usecase.usercase.accountUse.get.AccountOfficesListUseCase
import com.corp.sunlightdesign.usecase.usercase.accountUse.get.AccountPackagesListUseCase
import com.corp.sunlightdesign.usecase.usercase.accountUse.get.AccountUsersListUseCase
import com.corp.sunlightdesign.usecase.usercase.accountUse.post.AccountAddPartnerUseCase
import com.corp.sunlightdesign.usecase.usercase.accountUse.post.AccountCreateOrderUseCase
import com.corp.sunlightdesign.usecase.usercase.accountUse.post.AccountSetPackagesUseCase
import com.corp.sunlightdesign.usecase.usercase.accountUse.post.SetPackage
import com.corp.sunlightdesign.usecase.usercase.orders.CalculateDeliveryUseCase
import com.corp.sunlightdesign.usecase.usercase.profileUse.get.GetInvitesUseCase
import com.corp.sunlightdesign.usecase.usercase.profileUse.get.ProfileInfoUseCase
import com.corp.sunlightdesign.usecase.usercase.profileUse.post.ChangePassword
import com.corp.sunlightdesign.usecase.usercase.profileUse.post.ProfileChangeAvatarUseCase
import com.corp.sunlightdesign.usecase.usercase.profileUse.post.ProfileChangePasswordUseCase
import com.corp.sunlightdesign.utils.Constants
import com.corp.sunlightdesign.utils.ErrorListException
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import timber.log.Timber
import java.io.InputStream


/**
 * ViewModel for the task list screen.
 */
class ProfileViewModel constructor(
    private val sharedUseCase: SharedUseCase,
    private val accountCountriesUseCase: AccountCountriesUseCase,
    private val accountUsersListUseCase: AccountUsersListUseCase,
    private val accountPackagesListUseCase: AccountPackagesListUseCase,
    private val accountOfficesListUseCase: AccountOfficesListUseCase,
    private val accountSetPackagesUseCase: AccountSetPackagesUseCase,
    private val accountAddPartnerUseCase: AccountAddPartnerUseCase,
    private val accountCreateOrderUseCase: AccountCreateOrderUseCase,
    private val profileInfoUseCase: ProfileInfoUseCase,
    private val profileChangePasswordUseCase: ProfileChangePasswordUseCase,
    private val profileChangeAvatarUseCase: ProfileChangeAvatarUseCase,
    private val profileGetInvitesUseCase: GetInvitesUseCase,
    private val calculateDeliveryUseCase: CalculateDeliveryUseCase
) : StrongViewModel() {

    var progress = MutableLiveData<Boolean>(false)

    var createOrderPartnerBuilder: CreateOrderPartner.Builder = CreateOrderPartner.Builder()

    var registerItselfUserId = MutableLiveData<Int>()

    var errorsMap = MutableLiveData<Map<String, List<String>>>()

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

    private var _avatarImage = MutableLiveData<String?>()
    val avatarImage: LiveData<String?> get() = _avatarImage

    private var _productsList = MutableLiveData<List<Product>?>()
    val productsList: LiveData<List<Product>?> get() = _productsList

    private var _officesList = MutableLiveData<OfficesList>()
    val officeList: LiveData<OfficesList> get() = _officesList

    private var _profileInfo = MutableLiveData<UserInfo>()
    val profileInfo: LiveData<UserInfo> get() = _profileInfo

    private var _invites = MutableLiveData<InvitedResponse>()
    val invites: LiveData<InvitedResponse> get() = _invites

    private var _deliverService = MutableLiveData<DeliveryServiceListResponse>()
    val deliverService: LiveData<DeliveryServiceListResponse> get() = _deliverService

    private var _navigationEvent = MutableLiveData<NavigationEvent<Any?>?>()
    val navigationEvent: LiveData<NavigationEvent<Any?>?> get() = _navigationEvent

    var isFingerprintEnabled: Boolean
        get() = sharedUseCase.getSharedPreference().isFingerprintEnabled ?: false
        set(value) = setIsFingerprintEnabled(value)

    var isPinEnabled: Boolean
        get() = sharedUseCase.getSharedPreference().isPinEnabled ?: false
        set(value) = setIsPinEnabled(value)

    private fun setIsFingerprintEnabled(isEnabled: Boolean) {
        sharedUseCase.getSharedPreference().isFingerprintEnabled = isEnabled
    }

    private fun setIsPinEnabled(isEnabled: Boolean) {
        sharedUseCase.getSharedPreference().isPinEnabled = isEnabled
    }

    fun setPin(pin: String) {
        isPinEnabled = true
        sharedUseCase.getSharedPreference().pin = pin
    }

    fun nullifyData() {
        sharedUseCase.getSharedPreference().bearerToken = ""
        sharedUseCase.getSharedPreference().editPassword = ""
        sharedUseCase.getSharedPreference().pin = null
        sharedUseCase.getSharedPreference().isPinEnabled = false
        sharedUseCase.getSharedPreference().isFingerprintEnabled = false
    }

    fun getCountriesList() {
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

    fun getUsersList() {
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

    fun getPackagesList() {
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

    fun getOfficesList() {
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

    fun getProfileInfo() {
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

    fun changePassword(changePassword: ChangePassword) {
        progress.postValue(true)
        profileChangePasswordUseCase.setData(changePassword)
        profileChangePasswordUseCase.execute {
            onComplete {
                progress.postValue(false)
                _navigationEvent.postValue(NavigationEvent.NoAction)
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

    private fun changeAvatar(path: Uri?) {
        if(path == null) return
        progress.postValue(true)

        withActivity {
            val inputStream: InputStream? =
                it.contentResolver?.openInputStream(path)
            val part = MultipartBody.Part.createFormData(
                "user_avatar_path", "file.jpg", RequestBody.create(
                    MediaType.parse("image/*"),
                    inputStream!!.readBytes()
                )
            )
            profileChangeAvatarUseCase.setData(part)
            profileChangeAvatarUseCase.execute {
                onComplete { response ->
                    progress.postValue(false)
                    response?.user?.user_avatar_path?.let{ avatar ->
                        it.findViewById<CircleImageView>(R.id.userAvatarCircleImageView).setImageURI(path)
                    }
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

    fun addPartner(addPartner: AddPartner) {
        progress.postValue(true)
        accountAddPartnerUseCase.setData(addPartner)
        accountAddPartnerUseCase.execute {
            onComplete {
                progress.postValue(false)
                _navigationEvent.postValue(NavigationEvent.NavigateNext(data = it))
            }
            onNetworkError {
                progress.postValue(false)
                handleError(errorMessage = it.message)
            }
            onError {
                progress.postValue(false)
                when (it) {
                    is ErrorListException -> {
                        errorsMap.postValue(it.errorMap)
                        handleError(errorMessage = it.errorMessage)
                    }
                    else ->
                        handleError(throwable = it)
                }
            }
        }
    }

    fun nullifyNavigation() {
        _navigationEvent.postValue(NavigationEvent.NoAction)
    }

    fun setPackages(setPackage: SetPackage) {
        progress.postValue(true)
        accountSetPackagesUseCase.setData(setPackage)
        accountSetPackagesUseCase.execute {
            onComplete {
                progress.postValue(false)
                _navigationEvent.postValue(NavigationEvent.NavigateNext(data = it))
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

    fun createOrder(createOrder: CreateOrderPartner) {
        progress.postValue(true)
        accountCreateOrderUseCase.setData(createOrder)
        accountCreateOrderUseCase.execute {
            onComplete {
                progress.postValue(false)
                _navigationEvent.postValue(
                    NavigationEvent.NavigateNext(data = it)
                )
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

    fun getInvites(page: Int) {
        progress.postValue(true)
        profileGetInvitesUseCase.setPage(page)
        profileGetInvitesUseCase.execute {
            onComplete {
                progress.postValue(false)
                _invites.postValue(it)
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

    fun calculateDelivery(request: CalculateDeliveryUseCase.Request) {
        progress.postValue(true)
        calculateDeliveryUseCase.setModel(request)
        calculateDeliveryUseCase.execute {
            onComplete {
                progress.postValue(false)
                _deliverService.postValue(it)
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

    fun onAttachDocument(requestCode: Int = Constants.ACTION_IMAGE_CONTENT_INTENT_CODE) {
        withActivity {
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
        Timber.d(_packageList.value?.packages?.size.toString())
        _productsList.postValue(
            _packageList.value?.packages?.get(index)?.products
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                Constants.ACTION_IMAGE_CONTENT_INTENT_CODE -> {
                    Timber.d("Image path: ${data.data}")
                    if (_rearDocument.value == null) {
                        _rearDocument.postValue(data.data)
                    } else if (_backDocument.value == null) {
                        _backDocument.postValue(data.data)
                    }
                }
                Constants.ACTION_IMAGE_CONTENT_AVATAR_CODE -> {
                    Timber.d("Image path: ${data.data}")
                    changeAvatar(data.data)
                }
            }
        }
    }

    sealed class NavigationEvent<out T>{
        class NavigateNext<T>(val data: T): NavigationEvent<T>()
        object NoAction : NavigationEvent<Nothing>()
    }
}

