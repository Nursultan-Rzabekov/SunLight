package com.example.sunlightdesign.ui.launcher.news

import android.os.Bundle
import android.text.Html
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.Post
import com.example.sunlightdesign.ui.base.StrongActivity
import com.example.sunlightdesign.ui.launcher.LauncherViewModel
import com.example.sunlightdesign.utils.DateUtils
import com.example.sunlightdesign.utils.getImageUrl
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        backBtn.setText(R.string.back)
        titleTextView.setText(R.string.news)

        setListeners()
        setObservers()

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
                progress_bar.isVisible = it
            })

            postItem.observe(this@NewsActivity, Observer {
                it?.let { post -> fillFields(post) }
            })
        }
    }

    private fun fillFields(post: Post) {
        newsTitleTextView.text = Html.fromHtml(post.title, Html.FROM_HTML_MODE_COMPACT)
        newsDescriptionTextView.text = Html.fromHtml(post.description, Html.FROM_HTML_MODE_COMPACT)
        newsDateTextView.text =
            DateUtils.reformatDateString(post.created_at, DateUtils.PATTERN_DD_MM_YYYY_HH_mm)
        newsContentTextView.text = Html.fromHtml(post.content, Html.FROM_HTML_MODE_COMPACT)

        Glide.with(this)
            .load(getImageUrl(post.image))
            .into(newsImageView)
    }
}