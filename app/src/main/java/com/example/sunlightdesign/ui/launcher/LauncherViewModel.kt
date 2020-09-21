package com.example.sunlightdesign.ui.launcher

import androidx.lifecycle.ViewModel
import com.example.sunlightdesign.data.source.AuthRepository
import com.example.sunlightdesign.ui.launcher.auth.AuthState
import com.example.sunlightdesign.usecase.usercase.authUse.GetLoginAuthUseCase
import com.example.sunlightdesign.usecase.usercase.authUse.SetLogin
import timber.log.Timber



/**
 * ViewModel for the task list screen.
 */
class LauncherViewModel  constructor(
    private val getItemsUseCase: GetLoginAuthUseCase
) : ViewModel() {


}


