package com.example.sunlightdesign.ui.launcher.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.example.sunlightdesign.BaseApplication
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.base.StrongActivity
import com.example.sunlightdesign.ui.launcher.LauncherViewModel
import com.example.sunlightdesign.ui.screens.MainActivity
import kotlinx.android.synthetic.main.activity_auth.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class AuthActivity : StrongActivity(), NavController.OnDestinationChangedListener
{
    private val viewModel: AuthViewModel by viewModel{
        parametersOf(this)
    }

    override val layoutId: Int
        get() = R.layout.activity_auth

    override fun onCreate(savedInstanceViewState: Bundle?) {
        super.onCreate(savedInstanceViewState)
        setSupportActionBar(auth_toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        findNavController(R.id.auth_nav_host_fragment).addOnDestinationChangedListener(this)

    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {

    }
}

















