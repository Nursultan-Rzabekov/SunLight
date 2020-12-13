package com.example.sunlightdesign.ui.screens.profile.invited

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.base.StrongActivity
import com.example.sunlightdesign.ui.screens.profile.ProfileViewModel
import com.example.sunlightdesign.ui.screens.profile.adapters.InvitedAdapter
import kotlinx.android.synthetic.main.activity_invited.*
import kotlinx.android.synthetic.main.toolbar_with_back.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class InvitedActivity : StrongActivity() {

    val viewModel: ProfileViewModel by viewModel()
    private lateinit var invitedAdapter: InvitedAdapter

    private var page: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invited)

        titleTextView.text = getString(R.string.invited)
        backBtn.text = getString(R.string.back)

        initRecycler()
        setObservers()
        setListeners()

        viewModel.getInvites(page)
    }

    private fun initRecycler() {
        invitedRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@InvitedActivity)
            invitedAdapter = InvitedAdapter()
            adapter = invitedAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastPosition = linearLayoutManager.findLastVisibleItemPosition()

                    Timber.d("$lastPosition position")
                    Timber.d("${invitedAdapter.itemCount} count")
                    Timber.d("$page page")

                    if (lastPosition == (invitedAdapter.itemCount - 1) &&
                        page != -1 ) {
                        viewModel.getInvites(page + 1)
                    }
                    swipeInvites.isRefreshing = false
                }
            })
        }
    }

    private fun setListeners() {
        swipeInvites.setOnRefreshListener {
            page = 1
            viewModel.getInvites(page)
        }
        backBtn.setOnClickListener {
            finish()
        }
    }

    private fun setObservers() {
        viewModel.apply {
            progress.observe(this@InvitedActivity, Observer {
                progress_bar.isVisible = it
            })

            invites.observe(this@InvitedActivity, Observer {
                swipeInvites.isRefreshing = false

                it.children?.data?.let { children ->
                    if (page == 1) {
                        invitedAdapter.setItems(children)
                    } else {
                        invitedAdapter.addItems(children)
                    }
                }

                if (it.children?.last_page == page) {
                    page = -1
                } else {
                    page++
                }
            })
        }
    }
}