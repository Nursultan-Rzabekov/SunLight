package com.example.sunlightdesign.ui.screens.home.structure

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.base.StrongActivity
import kotlinx.android.synthetic.main.activity_structure.*

class StructureActivity : StrongActivity(), NavController.OnDestinationChangedListener{

    override val layoutId: Int
        get() = R.layout.activity_structure

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(structureToolbar)
        structureToolbar.setNavigationIcon(R.drawable.ic_close)
        structureToolbar.setNavigationOnClickListener { finish() }

        findNavController(R.id.structure_nav_host_fragment).addOnDestinationChangedListener(this)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {

    }
}