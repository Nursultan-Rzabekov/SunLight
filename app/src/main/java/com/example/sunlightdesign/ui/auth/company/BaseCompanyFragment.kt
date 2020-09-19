package com.example.sunlightdesign.ui.auth.company

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.sunlightdesign.ui.auth.login.LoginViewModel
import javax.inject.Inject

abstract class BaseCompanyFragment: Fragment() {

    val TAG: String = "AppDebug"

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel by viewModels<LoginViewModel> { viewModelFactory }

}



























