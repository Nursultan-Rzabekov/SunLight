package com.example.sunlightdesign.ui.screens.profile.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Office
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.profile.ProfileViewModel
import com.example.sunlightdesign.ui.screens.profile.register.adapters.OfficesRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_register_partner_step_four.*


class RegisterFragmentStepFour : StrongFragment<ProfileViewModel>(ProfileViewModel::class),
    OfficesRecyclerAdapter.OfficeSelector {

    private val officesRecyclerAdapter by lazy {
        return@lazy OfficesRecyclerAdapter(requireContext(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceViewState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_partner_step_four, container, false)
    }

    override fun onActivityCreated(savedInstanceViewState: Bundle?) {
        super.onActivityCreated(savedInstanceViewState)

        setListeners()
        setObservers()

        fragment_register_partner_step_four_rv.layoutManager = LinearLayoutManager(requireContext())
        fragment_register_partner_step_four_rv.adapter = officesRecyclerAdapter

        viewModel.getOfficesList()
    }

    private fun setObservers() {
        viewModel.apply {
            officeList.observe(viewLifecycleOwner, Observer {
                it?.let {
                    officesRecyclerAdapter.setItems(it.offices as ArrayList<Office?>)
                }
            })
        }
    }

    private fun setListeners() {
        btn_next_step_four.setOnClickListener {
            findNavController().navigate(R.id.action_stepFourFragment_to_stepFiveFragment)
        }
    }

    override fun onOfficeSelected(id: Int) {
        viewModel.createOrderPartnerBuilder.office_id =
            viewModel.officeList.value?.offices?.get(id)?.id ?: -1
    }

}
