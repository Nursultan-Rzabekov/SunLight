package com.example.sunlightdesign.ui.launcher.auth

import androidx.lifecycle.MutableLiveData
import com.example.sunlightdesign.ui.base.StrongViewModel
import com.example.sunlightdesign.ui.screens.MainActivity
import com.example.sunlightdesign.usecase.usercase.authUse.GetLoginAuthUseCase
import com.example.sunlightdesign.usecase.usercase.authUse.SetLogin
import com.example.sunlightdesign.utils.startNewActivity
import timber.log.Timber

/**
 * ViewModel for the task list screen.
 */
class AuthViewModel constructor(
    private val getItemsUseCase: GetLoginAuthUseCase
) : StrongViewModel() {

    val progress = MutableLiveData<Boolean>()

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




