

package com.example.sunlightdesign.ui.screens.email

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.email.entity.Data
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.email.adapters.AnnouncementsRecyclerAdapter
import kotlinx.android.synthetic.main.announcements.*


class EmailFragment : StrongFragment<EmailViewModel>(EmailViewModel::class), AnnouncementsRecyclerAdapter.AnnouncementSelector {

    private lateinit var announcementsRecyclerAdapter: AnnouncementsRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.announcements, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getAnnouncementsList()
    }

    override fun onAnnouncementSelected(id: Int) {
        viewModel.apply {
            announcementList.observe(viewLifecycleOwner, Observer {
                initRecyclerView(items = it.announcements?.data ?: listOf())
            })
        }
    }

    private fun initRecyclerView(items: List<Data>){
        announcementRecyclerView.apply {
            announcementsRecyclerAdapter = AnnouncementsRecyclerAdapter(context = requireContext(), announcements = this@EmailFragment, items = items)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = announcementsRecyclerAdapter
        }
    }
}
