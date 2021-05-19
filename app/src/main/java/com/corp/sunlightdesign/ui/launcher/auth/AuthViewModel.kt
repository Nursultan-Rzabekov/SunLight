package com.corp.sunlightdesign.ui.launcher.auth

import androidx.lifecycle.MutableLiveData
import com.corp.sunlightdesign.ui.base.StrongViewModel
import com.corp.sunlightdesign.usecase.usercase.SharedUseCase
import com.corp.sunlightdesign.usecase.usercase.authUse.GetLoginAuthUseCase
import com.corp.sunlightdesign.usecase.usercase.authUse.SetLogin
import com.corp.sunlightdesign.utils.ErrorListException
import com.corp.sunlightdesign.utils.views.PhoneInfo
import timber.log.Timber

/**
 * ViewModel for the task list screen.
 */
class AuthViewModel constructor(
    val sharedUseCase: SharedUseCase,
    private val getItemsUseCase: GetLoginAuthUseCase
) : StrongViewModel() {

    val progress = MutableLiveData<Boolean>()

    private var _phoneNumber = MutableLiveData<PhoneInfo>()
    val phoneNumber get() = _phoneNumber

    private var _password= MutableLiveData<String>()
    val password get() = _password

    private var _pin = MutableLiveData<String>()
    val pin get() = _pin

    private var _isLoginSuccess = MutableLiveData<Boolean>()
    val isLoginSuccess get() = _isLoginSuccess

    init {
        val phonePair = sharedUseCase.getSharedPreference().phoneNumber
        if (phonePair != null && !sharedUseCase.getSharedPreference().password.isNullOrEmpty()) {
            _password.postValue(sharedUseCase.getSharedPreference().password.orEmpty())
        }
        _phoneNumber.postValue(phonePair)

        if (!sharedUseCase.getSharedPreference().pin.isNullOrBlank()) {
            _pin.postValue(sharedUseCase.getSharedPreference().pin)
        }
    }

    fun setIsFingerprintEnabled(isEnabled: Boolean) {
        sharedUseCase.getSharedPreference().isFingerprintEnabled = isEnabled
    }

    fun setPin(pin: String) {
        sharedUseCase.getSharedPreference().pin = pin
        sharedUseCase.getSharedPreference().isPinEnabled = true
    }

    fun clearPin() {
        sharedUseCase.getSharedPreference().pin = null
        sharedUseCase.getSharedPreference().isPinEnabled = false
    }

    fun setPhoneAndPassword(phoneNumber: PhoneInfo, password:String){
        sharedUseCase.getSharedPreference().phoneNumber = phoneNumber
        sharedUseCase.getSharedPreference().password = password
    }

    fun cachePhone(phoneNumber: PhoneInfo) {
        sharedUseCase.getSharedPreference().phoneNumber = phoneNumber
    }

    fun getUseCase(phone: PhoneInfo, password: String) {
        val setLogin = SetLogin(
            phone = phone.phone,
            password = password
        )
        progress.value = true
        getItemsUseCase.setData(setLogin)

        getItemsUseCase.execute {
            onComplete {
                progress.value = false
                Timber.e("onComplete: %s", it)
                it?.errors?.let { errors ->
                    if (errors.isNotEmpty()) handleError(errorMessage = errors.first())
                    return@onComplete
                }

                cachePhone(phone)
                sharedPreferences.editPassword = setLogin.password
                _isLoginSuccess.postValue(true)
            }
            onNetworkError {
                progress.value = false
                it.message?.let { message ->
                    progress.value = false
                    handleError(errorMessage = message)
                }
            }
            onError {
                progress.value = false
                when (it) {
                    is ErrorListException -> {
                        val firstMessage = it.errorMap?.values?.firstOrNull()?.firstOrNull()
                        handleError(errorMessage = firstMessage.toString())
                    }
                    else -> handleError(throwable = it)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        getItemsUseCase.unsubscribe()
    }

}




