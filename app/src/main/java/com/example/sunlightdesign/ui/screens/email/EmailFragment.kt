package com.example.sunlightdesign.ui.screens.email

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.email.entity.Data
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.email.adapters.AnnouncementsRecyclerAdapter
import kotlinx.android.synthetic.main.announcements.*


class EmailFragment : StrongFragment<EmailViewModel>(EmailViewModel::class),
    AnnouncementsRecyclerAdapter.AnnouncementSelector {

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

        configViewModel()
        viewModel.getAnnouncementsList()

    }

    private fun configViewModel() {
        viewModel.apply {
            progress.observe(viewLifecycleOwner, Observer {
                progress_bar.visibility = if (it) View.VISIBLE else View.GONE
            })

            announcementList.observe(viewLifecycleOwner, Observer {
                initRecyclerView(items = it.announcements?.data ?: listOf())
            })
        }
    }

    override fun onAnnouncementSelected(id: Int) {
        val bundle = bundleOf("itemId" to id)
        findNavController().navigate(R.id.action_emailFragment_to_emailDetailsFragment, bundle)
    }

    private fun initRecyclerView(items: List<Data>) {
        announcementRecyclerView.apply {
            announcementsRecyclerAdapter = AnnouncementsRecyclerAdapter(
                context = requireContext(),
                announcements = this@EmailFragment,
                items = items
            )
            layoutManager = LinearLayoutManager(requireContext())
            adapter = announcementsRecyclerAdapter
        }
    }
}
