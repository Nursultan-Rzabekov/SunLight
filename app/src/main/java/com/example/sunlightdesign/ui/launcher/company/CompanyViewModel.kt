package com.example.sunlightdesign.ui.launcher.company

import androidx.lifecycle.ViewModel
import com.example.sunlightdesign.data.source.AuthRepository
import com.example.sunlightdesign.usecase.usercase.authUse.GetLoginAuthUseCase
import timber.log.Timber
import javax.inject.Inject


class CompanyViewModel @Inject constructor(
    private val getItemsUseCase: GetLoginAuthUseCase
) : ViewModel() {



}


