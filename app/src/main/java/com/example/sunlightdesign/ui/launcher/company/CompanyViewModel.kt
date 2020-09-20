package com.example.sunlightdesign.ui.launcher.company

import androidx.lifecycle.ViewModel
import com.example.sunlightdesign.data.source.AuthRepository
import com.example.sunlightdesign.usecase.usercase.authUse.GetLoginAuthUseCase
import timber.log.Timber
import javax.inject.Inject


class CompanyViewModel @Inject constructor(
    private val getItemsUseCase: GetLoginAuthUseCase,
    private val tasksRepository: AuthRepository
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


