package com.example.sunlightdesign.ui.launcher.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.sunlightdesign.di.TodoViewModelFactory
import com.example.sunlightdesign.ui.launcher.auth.di.AuthModule
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseAuthFragment: Fragment() {

    val TAG: String = "AppDebug"

    @Inject
    lateinit var viewModelFactory: TodoViewModelFactory

    lateinit var viewModel : AuthViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this,viewModelFactory).get(AuthViewModel::class.java)
        } ?: throw Exception("Error")
    }


}



























