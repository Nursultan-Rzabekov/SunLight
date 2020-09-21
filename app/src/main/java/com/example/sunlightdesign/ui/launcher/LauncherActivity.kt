package com.example.sunlightdesign.ui.launcher

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.example.sunlightdesign.BaseApplication
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.base.StrongActivity
import com.example.sunlightdesign.ui.screens.MainActivity
import kotlinx.android.synthetic.main.activity_auth.*


class LauncherActivity : StrongActivity(), NavController.OnDestinationChangedListener
{

    override val layoutId: Int
        get() = R.layout.activity_launcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findNavController(R.id.launcher_nav_host_fragment).addOnDestinationChangedListener(this)

    }

    fun navMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun onFinishCheckPreviousAuthUser(){
        fragment_container.visibility = View.VISIBLE
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {

    }
}

















