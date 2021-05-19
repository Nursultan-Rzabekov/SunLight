package com.corp.sunlightdesign.ui.launcher.auth

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.ui.base.StrongActivity
import kotlinx.android.synthetic.main.activity_auth.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class AuthActivity : StrongActivity(), NavController.OnDestinationChangedListener {
    private val viewModel: AuthViewModel by viewModel {
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

















