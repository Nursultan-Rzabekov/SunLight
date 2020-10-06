package com.example.sunlightdesign.ui.base


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.sunlightdesign.R
import timber.log.Timber

abstract class StrongActivity: AppCompatActivity() {

    open val isLightStatusBar = true
    open val fragmentContainerId get() = R.id.fragment_container
    open val layoutId = R.layout.fragment_activity

    private val FragmentManager.currentNavigationFragment: Fragment?
        get() = findFragmentById(fragmentContainerId)?.childFragmentManager?.fragments?.first()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        //add status bar custom color
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragment = supportFragmentManager.currentNavigationFragment ?: return
        fragment.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val fragment = supportFragmentManager.currentNavigationFragment ?: return
        fragment.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
