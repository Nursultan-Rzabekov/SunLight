package com.example.sunlightdesign.ui.launcher

import androidx.lifecycle.MutableLiveData
import com.example.sunlightdesign.ui.base.StrongViewModel
import com.example.sunlightdesign.usecase.usercase.SharedUseCase

/**
 * ViewModel for the task list screen.
 */

class LauncherViewModel constructor(
    private val sharedUseCase: SharedUseCase
) : StrongViewModel() {

    private var _bearerToken = MutableLiveData<Boolean>(false)
    val bearerToken get() = _bearerToken

    init {
        if (!sharedUseCase.getSharedPreference().bearerToken.isNullOrEmpty())
            _bearerToken.postValue(true)
    }

}


