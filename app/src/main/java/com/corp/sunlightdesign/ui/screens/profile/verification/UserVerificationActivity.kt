package com.corp.sunlightdesign.ui.screens.profile.verification

import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.ui.base.StrongActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserVerificationActivity : StrongActivity(), NavController.OnDestinationChangedListener {

    val viewModel: UserVerificationViewModel by viewModel()

    override val layoutId: Int
        get() = R.layout.activity_user_verification

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findNavController(R.id.verification_nav_host_fragment).addOnDestinationChangedListener(this)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) = Unit

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.onActivityResult(requestCode, resultCode, data)
    }
}