package com.example.sunlightdesign.ui.launcher.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sunlightdesign.data.source.remote.auth.entity.Login
import com.example.sunlightdesign.usecase.usercase.authUse.GetLoginAuthUseCase
import com.example.sunlightdesign.usecase.usercase.authUse.SetLogin
import timber.log.Timber



/**
 * ViewModel for the task list screen.
 */
class AuthViewModel constructor(
    private val getItemsUseCase: GetLoginAuthUseCase
) : ViewModel() {

    private val _authState = MutableLiveData<AuthState<Login>>()
    val authState: LiveData<AuthState<Login>> get() = _authState

    fun getUseCase(setLogin: SetLogin){
        _authState.postValue(AuthState.Loading)
        getItemsUseCase.setData(setLogin)

        getItemsUseCase.execute {
            onComplete {
                Timber.e("onComplete: %s", it)
                it?.errors?.let {errors ->
                    if (errors.isNotEmpty())
                        _authState.postValue(AuthState.Error(errors.first()))
                }
                it?.let {
                    _authState.postValue(AuthState.Success(it))
                }
            }
            onNetworkError {
                _authState.postValue(AuthState.Error(it.message?:"Something went wrong"))
                Timber.e(it.toString()) }
            onError {
                _authState.postValue(AuthState.Error(it.localizedMessage?:"Error"))
                Timber.e(it) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        getItemsUseCase.unsubscribe()
    }

}

sealed class AuthState<out T>{
    class Success<T>(data: T): AuthState<T>()
    class Error<T>(val message: String): AuthState<T>()
    object Loading: AuthState<Nothing>()
}


