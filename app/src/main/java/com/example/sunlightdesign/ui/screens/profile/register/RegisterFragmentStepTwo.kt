package com.example.sunlightdesign.ui.screens.profile.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Package
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.User
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.profile.ProfileViewModel
import com.example.sunlightdesign.ui.screens.profile.register.adapters.PackageRecyclerAdapter
import com.example.sunlightdesign.usecase.usercase.accountUse.post.SetPackage
import com.example.sunlightdesign.utils.showMessage
import kotlinx.android.synthetic.main.fragment_register_partner_step_two.*
import kotlinx.android.synthetic.main.fragment_register_partner_step_two.progress_bar


class RegisterFragmentStepTwo : StrongFragment<ProfileViewModel>(ProfileViewModel::class),
    PackageRecyclerAdapter.PackageSelector {

    private var packageEntity: Package? = null

    private val packageRecyclerAdapter: PackageRecyclerAdapter by lazy {
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
        packageEntity = viewModel.packageList.value?.packages?.get(id)
        viewModel.onPackageSelected(id)
    }

    private fun setObservers() {
        viewModel.apply {
            progress.observe(viewLifecycleOwner, Observer {
                progress_bar.visibility = if (it) View.VISIBLE else View.GONE
            })

            packageList.observe(viewLifecycleOwner, Observer {
                it?.packages?.let { packages ->
                    packageRecyclerAdapter.setItems(ArrayList(packages))
                }
            })

            navigationEvent.observe(viewLifecycleOwner, Observer {
                if (it != null &&
                    it is ProfileViewModel.NavigationEvent.NavigateNext &&
                    it.data is User?){
                    val bundle = bundleOf(
                        RegisterFragmentStepThree.PACKAGE_NAME to packageEntity?.package_name,
                        RegisterFragmentStepThree.PACKAGE_SUM_KZT to packageEntity?.package_price_in_kzt,
                        RegisterFragmentStepThree.PACKAGE_SUM_BV to packageEntity?.package_price
                    )
                    findNavController().navigate(R.id.action_stepTwoFragment_to_stepThreeFragment, bundle)
                    nullifyNavigation()
                }
            })
        }
    }

    private fun setLayoutManager(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setListeners() {
        registration_partner_step_two_next_button.setOnClickListener {
            if (!checkFields()) return@setOnClickListener
            arguments?.let {
                viewModel.setPackages(
                    SetPackage(
                        userId = it.getInt(RegisterFragmentStepOne.USER_ID, -1),
                        packageId = packageEntity?.id ?: -1
                    )
                )
            }
        }

        backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun checkFields() : Boolean {
        val message = when {
            packageEntity == null || packageEntity?.id == null -> "Выбирите пакет"
            else -> null
        }
        if (message != null) {
            showMessage(
                context = requireContext(),
                message = message
            )
            return false
        }
        return true
    }

}
