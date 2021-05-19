package com.corp.sunlightdesign.ui.screens.email

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.ui.base.StrongActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class EmailDetailActivity : StrongActivity(), NavController.OnDestinationChangedListener {

    val viewModel: EmailViewModel by viewModel()

    companion object {
        const val KEY_ANNOUNCEMENT_ID = "announcement_id"
    }

    override val layoutId: Int
        get() = R.layout.activity_email

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.itemId.id = intent?.getIntExtra(KEY_ANNOUNCEMENT_ID, -1) ?: -1

        findNavController(R.id.email_nav_host_fragment).addOnDestinationChangedListener(this)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {


    }
}