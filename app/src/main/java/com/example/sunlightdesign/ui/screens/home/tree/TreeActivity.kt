package com.example.sunlightdesign.ui.screens.home.tree

import android.os.Bundle
import android.view.KeyEvent
import com.example.sunlightdesign.BuildConfig
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.base.StrongActivity
import kotlinx.android.synthetic.main.activity_users_tree.*

class TreeActivity: StrongActivity() {

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

    private fun loadWebView(){
        treeWebView.settings.loadWithOverviewMode = true
        treeWebView.settings.useWideViewPort = true
        treeWebView.settings.javaScriptEnabled = true
        treeWebView.settings.loadsImagesAutomatically = true
        treeWebView.loadUrl(BuildConfig.STRUCTURE_URL)
    }

    private fun setListeners() {
        exitBtn.setOnClickListener { finish() }
    }
}