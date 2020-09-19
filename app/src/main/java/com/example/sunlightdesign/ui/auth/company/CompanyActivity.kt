package com.example.sunlightdesign.ui.auth.company

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.base.StrongActivity

class CompanyActivity : StrongActivity(), NavController.OnDestinationChangedListener
{
    override val layoutId: Int
        get() = R.layout.activity_company

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findNavController(R.id.company_nav_host_fragment).addOnDestinationChangedListener(this)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {

    }
}

















