package com.example.sunlightdesign.ui.auth

import androidx.lifecycle.ViewModel
import com.example.sunlightdesign.data.source.TasksRepository
import com.example.sunlightdesign.usecase.usercase.authUse.GetLoginAuthUseCase
import timber.log.Timber
import javax.inject.Inject


/**
 * ViewModel for the task list screen.
 */
class AuthViewModel @Inject constructor(
    private val getItemsUseCase: GetLoginAuthUseCase,
    private val tasksRepository: TasksRepository
) : ViewModel() {

    fun getUseCase(){
        getItemsUseCase.execute {
            onComplete {
                Timber.e("onComplete: %s", it.size)
            }
            onNetworkError { Timber.e(it.toString()) }
            onError { Timber.e(it) }
        }
    }

}


