package com.corp.sunlightdesign.ui.screens.profile.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.ui.base.StrongFragment
import com.corp.sunlightdesign.ui.screens.profile.ProfileViewModel
import com.corp.sunlightdesign.utils.showMessage
import kotlinx.android.synthetic.main.fragment_register_delivery_address_step_four.*

class DeliveryAddressRegisterStepFourFragment :
    StrongFragment<ProfileViewModel>(ProfileViewModel::class) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceViewState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_register_delivery_address_step_four,
            container,
            false
        )
    }

    override fun onActivityCreated(savedInstanceViewState: Bundle?) {
        super.onActivityCreated(savedInstanceViewState)
        setupListeners()
    }

    private fun setupListeners() {
        btn_next_step_four.setOnClickListener {
            if (!checkFields()) return@setOnClickListener showMessage(
                requireContext(),
                message = getString(R.string.fill_all_the_field)
            )
            viewModel.createOrderPartnerBuilder.deliveryInfo?.address =
                streetAddressEditText.text.toString()
            viewModel.createOrderPartnerBuilder.deliveryInfo?.fio = partnerEditText.text.toString()
            findNavController().navigate(R.id.action_deliveryAddressRegisterStepFourFragment_to_register_fragment_step_five)
        }
        backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun checkFields(): Boolean {
        return !partnerEditText.text.isNullOrBlank()
                && !streetAddressEditText.text.isNullOrBlank()
    }
}