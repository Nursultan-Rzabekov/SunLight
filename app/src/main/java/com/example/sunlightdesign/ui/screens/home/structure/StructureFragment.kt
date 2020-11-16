package com.example.sunlightdesign.ui.screens.home.structure

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.StructureInfo
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.home.tree.TreeActivity
import kotlinx.android.synthetic.main.activity_structure.*
import kotlinx.android.synthetic.main.fragment_structure.*
import kotlinx.android.synthetic.main.fragment_structure.progress_bar
import kotlinx.android.synthetic.main.structure_status_row_item.view.*

class StructureFragment : StrongFragment<StructureViewModel>(StructureViewModel::class) {

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
        ownInvitesTextView.text = structure.invited.toString()

        var totalUsers = 0
        structure.statuses?.let {
            for (status in structure.statuses) {
                val tableRow = layoutInflater.inflate(R.layout.structure_status_row_item, structureInfoTableLayout, false)
                tableRow.rowFirstColumnTextView.text = status.status_name
                tableRow.rowSecondColumnTextView.text = status.users_count.toString()
                structureInfoTableLayout.addView(tableRow)
                totalUsers += status.users_count ?: 0
            }
        }
        usersInStructureTextView.text = totalUsers.toString()
    }
}