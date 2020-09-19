package com.example.sunlightdesign.ui.launcher.auth

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

abstract class BaseAuthFragment: Fragment() {

    val TAG: String = "AppDebug"

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel by viewModels<AuthViewModel> { viewModelFactory }

}



























