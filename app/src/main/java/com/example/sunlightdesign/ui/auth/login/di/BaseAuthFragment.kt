package com.example.sunlightdesign.ui.auth.login.di

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.sunlightdesign.ui.auth.login.LoginViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseAuthFragment: DaggerFragment(){

    val TAG: String = "AppDebug"

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel : LoginViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
    }
}



























