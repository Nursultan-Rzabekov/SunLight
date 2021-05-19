package com.corp.sunlightdesign.ui.screens.home.tree

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import com.corp.sunlightdesign.BuildConfig
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.ui.base.StrongActivity
import com.corp.sunlightdesign.ui.launcher.LauncherViewModel
import kotlinx.android.synthetic.main.activity_users_tree.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TreeActivity: StrongActivity() {


    val viewModel: LauncherViewModel by viewModel()


    override val layoutId: Int
        get() = R.layout.activity_users_tree

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setListeners()
        loadWebView()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK && treeWebView.canGoBack()) {
            treeWebView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadWebView(){
        treeWebView.settings.loadWithOverviewMode = true
        treeWebView.settings.useWideViewPort = true
        treeWebView.settings.javaScriptEnabled = true
        treeWebView.settings.loadsImagesAutomatically = true
        treeWebView.settings.builtInZoomControls = true
        treeWebView.settings.domStorageEnabled = true
        treeWebView.loadUrl(BuildConfig.STRUCTURE_URL.plus(viewModel.getUserId()))
    }

    private fun setListeners() {
        exitBtn.setOnClickListener { finish() }
    }
}