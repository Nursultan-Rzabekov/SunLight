package com.example.sunlightdesign.ui.base


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sunlightdesign.R

abstract class StrongActivity: AppCompatActivity() {

    open val isLightStatusBar = true
    open val fragmentContainerId get() = R.id.fragment_container
    open val layoutId = R.layout.fragment_activity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        //add status bar custom color
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragment = supportFragmentManager.findFragmentById(fragmentContainerId) ?: return
        fragment.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val fragment = supportFragmentManager.findFragmentById(fragmentContainerId) ?: return
        fragment.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
