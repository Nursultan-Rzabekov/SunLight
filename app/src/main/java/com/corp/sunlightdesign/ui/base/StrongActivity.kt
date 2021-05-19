package com.corp.sunlightdesign.ui.base


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.corp.sunlightdesign.R

abstract class StrongActivity : AppCompatActivity() {

    open val isLightStatusBar = true
    open val fragmentContainerId get() = R.id.fragment_container
    open val layoutId = R.layout.fragment_activity

    private val FragmentManager.currentNavigationFragment: List<Fragment>?
        get() = findFragmentById(fragmentContainerId)?.childFragmentManager?.fragments

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        //add status bar custom color
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        supportFragmentManager.fragments.forEach {
//            it.onActivityResult(requestCode, resultCode, data)
//        }
//        supportFragmentManager.currentNavigationFragment?.forEach {
//            it.onActivityResult(requestCode, resultCode, data)
//        }
//    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        supportFragmentManager.currentNavigationFragment?.forEach {
            it.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}
