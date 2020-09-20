package com.example.sunlightdesign.ui.launcher.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sunlightdesign.data.source.AuthRepository
import com.example.sunlightdesign.data.source.remote.auth.entity.Login
import com.example.sunlightdesign.data.source.remote.auth.entity.LoginResponse
import com.example.sunlightdesign.usecase.usercase.authUse.GetLoginAuthUseCase
import timber.log.Timber
import javax.inject.Inject


/**
 * ViewModel for the task list screen.
 */
class AuthViewModel @Inject constructor(
    private val getItemsUseCase: GetLoginAuthUseCase
) : ViewModel() {

    private val _authState = MutableLiveData<AuthState<Login>>()
    val authState: LiveData<AuthState<Login>> get() = _authState

    fun getUseCase(){
        _authState.postValue(AuthState.Loading)
        getItemsUseCase.execute {
            onComplete {
                Timber.e("onComplete: %s", it.login)
                it.errors?.let {errors ->
                    if (errors.isNotEmpty())
                        _authState.postValue(AuthState.Error(errors.first()))
                }
                it.login?.let {login ->
                    _authState.postValue(AuthState.Success(login))
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

}

sealed class AuthState<out T>{
    class Success<T>(data: T): AuthState<T>()
    class Error<T>(val message: String): AuthState<T>()
    object Loading: AuthState<Nothing>()
}


