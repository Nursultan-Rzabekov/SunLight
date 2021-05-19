package com.corp.sunlightdesign.ui.screens.profile.edit

import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.ui.base.StrongActivity
import com.corp.sunlightdesign.ui.screens.profile.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditPartnerActivity: StrongActivity(), NavController.OnDestinationChangedListener{

    val viewModel: ProfileViewModel by viewModel()

    override val layoutId: Int
        get() = R.layout.activity_edit_partner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findNavController(R.id.edit_nav_host_fragment).addOnDestinationChangedListener(this)
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