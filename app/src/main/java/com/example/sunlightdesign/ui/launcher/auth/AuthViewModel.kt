package com.example.sunlightdesign.ui.launcher.auth

import androidx.lifecycle.MutableLiveData
import com.example.sunlightdesign.ui.base.StrongViewModel
import com.example.sunlightdesign.ui.screens.MainActivity
import com.example.sunlightdesign.usecase.usercase.SharedUseCase
import com.example.sunlightdesign.usecase.usercase.authUse.GetLoginAuthUseCase
import com.example.sunlightdesign.usecase.usercase.authUse.SetLogin
import com.example.sunlightdesign.utils.startNewActivity
import timber.log.Timber

/**
 * ViewModel for the task list screen.
 */
class AuthViewModel constructor(
    val sharedUseCase: SharedUseCase,
    private val getItemsUseCase: GetLoginAuthUseCase
) : StrongViewModel() {

    val progress = MutableLiveData<Boolean>()

    private var _phoneNumber = MutableLiveData<String>()
    val phoneNumber get() = _phoneNumber

    private var _password= MutableLiveData<String>()
    val password get() = _password

    init {
        if (!sharedUseCase.getSharedPreference().phoneNumber.isNullOrEmpty() and
            !sharedUseCase.getSharedPreference().password.isNullOrEmpty())
            _phoneNumber.postValue(sharedUseCase.getSharedPreference().phoneNumber)
            _password.postValue(sharedUseCase.getSharedPreference().password)
    }

    fun setPhoneAndPassword(phoneNumber:String, password:String){
        sharedUseCase.getSharedPreference().phoneNumber = phoneNumber
        sharedUseCase.getSharedPreference().password = password
    }

    fun getUseCase(setLogin: SetLogin) {
        progress.value = true
        getItemsUseCase.setData(setLogin)

        getItemsUseCase.execute {
            onComplete {
                progress.value = false
                Timber.e("onComplete: %s", it)
                it?.errors?.let { errors ->
                    if (errors.isNotEmpty())
                        handleError(errorMessage = errors.first())
                }

                withActivity { activity ->
                    sharedPreferences.editPassword = setLogin.password
                    activity.startNewActivity(MainActivity::class)
                }
            }
            onNetworkError {
                it.message?.let { message ->
                    progress.value = false
                    handleError(errorMessage = message)
                }
            }
            onError {
                progress.value = false
                handleError(throwable = it)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        getItemsUseCase.unsubscribe()
    }

}




