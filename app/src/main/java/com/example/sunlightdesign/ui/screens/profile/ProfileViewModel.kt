package com.example.sunlightdesign.ui.screens.profile

import com.example.sunlightdesign.ui.base.StrongViewModel
import com.example.sunlightdesign.usecase.usercase.accountUse.GetAccountUseCase


/**
 * ViewModel for the task list screen.
 */
class ProfileViewModel  constructor(
    private val getAccountUseCase: GetAccountUseCase
) : StrongViewModel() {

    fun getCountriesList(){
        getAccountUseCase.execute {
            onComplete {  }
            onNetworkError {  }
            onError { }
        }
    }
}


