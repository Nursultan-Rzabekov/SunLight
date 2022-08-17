package com.corp.sunlightdesign.ui.launcher.news

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.data.source.dataSource.remote.main.entity.Post
import com.corp.sunlightdesign.ui.base.StrongActivity
import com.corp.sunlightdesign.ui.launcher.LauncherViewModel
import com.corp.sunlightdesign.utils.DateUtils
import com.corp.sunlightdesign.utils.getImageUrl
import kotlinx.android.synthetic.main.news_activity.*
import kotlinx.android.synthetic.main.toolbar_with_back.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsActivity : StrongActivity() {

    private val viewModel: LauncherViewModel by viewModel()

    companion object {
        const val KEY_POST_ID = "post_id"
    }

    override val layoutId: Int
        get() = R.layout.news_activity

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        backBtn.setText(R.string.back)
        titleTextView.setText(R.string.news)

        setListeners()
        setObservers()

        newsContentWebView.settings.loadWithOverviewMode = true
        newsContentWebView.settings.javaScriptEnabled = true
        newsContentWebView.settings.loadsImagesAutomatically = true
        newsContentWebView.settings.domStorageEnabled = true

        intent.getIntExtra(KEY_POST_ID, -1).let {
            if (it == -1) return@let
            viewModel.getPostById(it)
        }
    }

    private fun setListeners() {
        backBtn.setOnClickListener { finish() }
    }

    private fun setObservers() {
        viewModel.apply {
            progress.observe(this@NewsActivity, Observer {
                progress_bar.visibility = if(it) View.VISIBLE else View.GONE
            })

            postItem.observe(this@NewsActivity, Observer {
                it?.let { post -> fillFields(post) }
            })
        }
    }

    private fun fillFields(post: Post) {
        newsTitleTextView.text = Html.fromHtml(post.title, Html.FROM_HTML_MODE_COMPACT)
        newsDateTextView.text =
            DateUtils.reformatDateString(post.created_at, DateUtils.PATTERN_DD_MM_YYYY_HH_mm)
        newsContentWebView
            .loadData(post.content, "text/html; charset=utf-8", "UTF-8")

        Glide.with(this)
            .load(getImageUrl(post.image))
            .into(newsImageView)
    }
}