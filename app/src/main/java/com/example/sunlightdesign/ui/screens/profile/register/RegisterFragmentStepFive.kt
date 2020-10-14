package com.example.sunlightdesign.ui.screens.profile.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.profile.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_register_partner_step_five.*


class RegisterFragmentStepFive : StrongFragment<ProfileViewModel>(ProfileViewModel::class) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceViewState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_partner_step_five, container, false)
    }

    override fun onActivityCreated(savedInstanceViewState: Bundle?) {
        super.onActivityCreated(savedInstanceViewState)

        setListeners()
    }

    private fun setListeners() {
        registration_partner_step_five_payment_rg.setOnCheckedChangeListener { group, checkedId ->
            hidePayments()
            when(checkedId) {
                paymentByTillRbtn.id -> {}
                paymentByPayboxRbtn.id -> {}
                paymentByBvRbtn.id -> {showPaymentByBv()}
            }
        }
    }

    private fun hidePayments() {
        payByPayboxLayout.visibility = View.GONE
    }

    private fun showPaymentByBv() {
        payByPayboxLayout.visibility = View.VISIBLE
    }

}
