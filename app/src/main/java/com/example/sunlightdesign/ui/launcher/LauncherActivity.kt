package com.example.sunlightdesign.ui.launcher

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.base.StrongActivity
import com.example.sunlightdesign.ui.screens.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class LauncherActivity : StrongActivity(), NavController.OnDestinationChangedListener {

    val viewModel: LauncherViewModel by viewModel()
    override val layoutId: Int
        get() = R.layout.activity_launcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findNavController(R.id.launcher_nav_host_fragment).addOnDestinationChangedListener(this)
        setObservers()
    }

    private fun setObservers() {
        viewModel.bearerToken.observe(this, Observer {
            if (it) navMainActivity()
        })
    }

    private fun navMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {

    }
}

















