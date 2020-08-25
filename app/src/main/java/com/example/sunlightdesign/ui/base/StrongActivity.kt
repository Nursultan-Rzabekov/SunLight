package com.example.sunlightdesign.ui.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.sunlightdesign.R
import com.example.sunlightdesign.utils.areYouSureDialog
import com.example.sunlightdesign.utils.displayInfoDialog
import com.example.sunlightdesign.utils.displayToast

abstract class StrongActivity: AppCompatActivity(), UICommunicationListener {

    open val fragmentContainerId get() = R.id.fragment_container
    open val layoutId = R.layout.fragment_activity

    val TAG: String = "AppDebug"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
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

    override fun onUIMessageReceived(uiMessage: UIMessage) {
        when(uiMessage.uiMessageType){

            is UIMessageType.AreYouSureDialog -> {
                areYouSureDialog(
                    uiMessage.message,
                    uiMessage.uiMessageType.callback
                )
            }

            is UIMessageType.Toast -> {
                displayToast(uiMessage.message)
            }

            is UIMessageType.Dialog -> {
                displayInfoDialog(uiMessage.message)
            }

            is UIMessageType.None -> {
                Log.i(TAG, "onUIMessageReceived: ${uiMessage.message}")
            }
        }
    }

//    override fun isStoragePermissionGranted(): Boolean{
//        if (
//            ContextCompat.checkSelfPermission(this,
//                Manifest.permission.READ_EXTERNAL_STORAGE)
//            != PackageManager.PERMISSION_GRANTED &&
//            ContextCompat.checkSelfPermission(this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE)
//            != PackageManager.PERMISSION_GRANTED  ) {
//
//
//            ActivityCompat.requestPermissions(this,
//                arrayOf(
//                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//                ),
//                PERMISSIONS_REQUEST_READ_STORAGE
//            )
//
//            return false
//        } else {
//            // Permission has already been granted
//            return true
//        }
//    }
}
