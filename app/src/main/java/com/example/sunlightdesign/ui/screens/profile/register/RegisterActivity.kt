package com.example.sunlightdesign.ui.screens.profile.register

import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.base.StrongActivity
import com.example.sunlightdesign.ui.screens.profile.ProfileViewModel
import com.example.sunlightdesign.ui.screens.profile.register.RegisterFragmentStepOne.Companion.USER_ID
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class RegisterActivity : StrongActivity(), NavController.OnDestinationChangedListener {

    val viewModel: ProfileViewModel by viewModel()

    override val layoutId: Int
        get() = R.layout.activity_add_partner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.d(intent.getIntExtra(USER_ID, -1).toString())
        viewModel.registerItselfUserId.value = intent.getIntExtra(USER_ID, -1)

        findNavController(R.id.register_nav_host_fragment).addOnDestinationChangedListener(this)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.onActivityResult(requestCode, resultCode, data)
        Timber.d("asda")
    }
}