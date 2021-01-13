package com.example.sunlightdesign.ui.screens.profile.verification

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.base.StrongActivity
import com.example.sunlightdesign.ui.screens.profile.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserVerificationActivity : StrongActivity(), NavController.OnDestinationChangedListener {

    val viewModel: ProfileViewModel by viewModel()

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