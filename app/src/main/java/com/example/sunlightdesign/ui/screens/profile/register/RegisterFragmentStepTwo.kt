

package com.example.sunlightdesign.ui.screens.profile.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Package
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.profile.ProfileViewModel
import com.example.sunlightdesign.ui.screens.profile.register.adapters.PackageRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_register_partner_step_two.*


class RegisterFragmentStepTwo : StrongFragment<ProfileViewModel>(ProfileViewModel::class), PackageRecyclerAdapter.PackageSelector {

    private val packageRecyclerAdapter : PackageRecyclerAdapter by lazy {
        return@lazy PackageRecyclerAdapter(requireContext(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceViewState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_partner_step_two, container, false)
    }

    override fun onActivityCreated(savedInstanceViewState: Bundle?) {
        super.onActivityCreated(savedInstanceViewState)

        setListeners()
        setObservers()
        setLayoutManager(recyclerView = registration_partner_step_two_packs_rv)
        registration_partner_step_two_packs_rv.adapter = packageRecyclerAdapter


        viewModel.getPackagesList()
    }

    override fun onPackageSelected(id: Int) {
        viewModel.onPackageSelected(id)
    }

    private fun setObservers(){
        viewModel.apply {
            packageList.observe(viewLifecycleOwner, Observer {
                it?.let {packagesList ->
                    packageRecyclerAdapter.setItems(packagesList.packages as ArrayList<Package>)
                }

            })
        }
    }

    private fun setLayoutManager(recyclerView: RecyclerView){
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setListeners(){
        registration_partner_step_two_next_button.setOnClickListener {
            findNavController().navigate(R.id.action_stepTwoFragment_to_stepThreeFragment)
        }
    }

}
