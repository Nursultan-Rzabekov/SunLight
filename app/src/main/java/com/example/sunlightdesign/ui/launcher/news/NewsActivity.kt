package com.example.sunlightdesign.ui.launcher.news

import android.os.Bundle
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.base.StrongActivity
import kotlinx.android.synthetic.main.toolbar_with_back.*

class NewsActivity : StrongActivity() {

    override val layoutId: Int
        get() = R.layout.news_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        backBtn.setText(R.string.back)
        titleTextView.setText(R.string.news)

        setListeners()
    }

    private fun setListeners() {
        backBtn.setOnClickListener { finish() }
    }
}