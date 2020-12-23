package com.example.sunlightdesign.ui.screens.home.structure

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.StructureInfo
import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.User
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.home.structure.adapters.UserStatusesAdapter
import com.example.sunlightdesign.ui.screens.home.tree.TreeActivity
import kotlinx.android.synthetic.main.activity_structure.*
import kotlinx.android.synthetic.main.fragment_structure.*
import kotlinx.android.synthetic.main.fragment_structure.progress_bar
import kotlinx.android.synthetic.main.structure_status_row_item.view.*

class StructureFragment : StrongFragment<StructureViewModel>(StructureViewModel::class) {

    private lateinit var userStatusesAdapter: UserStatusesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_structure, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        setObservers()

        viewModel.getStructureInfo()
    }

    private fun setObservers() {
        viewModel.apply {
            structure.observe(viewLifecycleOwner, Observer {
                bindStructureInfo(it)
                if (it.status_update_list.isNullOrEmpty()) {
                    statusesLayout.visibility = View.GONE
                } else {
                    statusesLayout.visibility = View.VISIBLE
                    initRecyclerViews(it.status_update_list)
                }
            })
            progress.observe(viewLifecycleOwner, Observer {
                progress_bar.isVisible = it
            })
        }
    }

    private fun setListeners() {
        showTreeTextView.setOnClickListener {
            startActivity(Intent(requireContext(), TreeActivity::class.java))
        }
    }

    private fun bindStructureInfo(structure: StructureInfo) {
        structure.statuses?.let {
            structureInfoTableLayout.removeAllViews()
            structure.statuses.forEach {
                val tableRow = layoutInflater.inflate(R.layout.structure_status_row_item, structureInfoTableLayout, false)
                tableRow.rowFirstColumnTextView.text = it.status_name
                tableRow.rowSecondColumnTextView.text = it.users_count.toString()
                structureInfoTableLayout.addView(tableRow)
            }
        }
    }

    private fun initRecyclerViews(userStatuses: List<User>) {
        statusesHeaderTextView.text = getString(R.string.changes_in_statuses, userStatuses.size)
        userStatusesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            userStatusesAdapter = UserStatusesAdapter(userStatuses)
            adapter = userStatusesAdapter
        }
    }
}