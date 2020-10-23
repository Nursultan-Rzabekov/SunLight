package com.example.sunlightdesign.ui.screens.profile.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
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
        setObservers()
        registration_partner_step_five_payment_rg.check(paymentByTillRbtn.id)
    }

    private fun setObservers() {
        viewModel.apply {
            progress.observe(viewLifecycleOwner, Observer {

            })

            navigationEvent.observe(viewLifecycleOwner, Observer {
//                if (it != null &&
//                        it is ProfileViewModel.NavigationEvent.NavigateNext &&
//                        it.data is AddPartnerResponse?) {
//
//                }
            })
        }
    }

    private fun setListeners() {
        registration_partner_step_five_payment_rg.setOnCheckedChangeListener { group, checkedId ->
            hidePayments()
            when(checkedId) {
                paymentByTillRbtn.id -> {
                    viewModel.createOrderPartnerBuilder.order_payment_type = 1
                }
                paymentByPayboxRbtn.id -> {
                    viewModel.createOrderPartnerBuilder.order_payment_type = 2
                }
                paymentByBvRbtn.id -> {
                    viewModel.createOrderPartnerBuilder.order_payment_type = 3
                    showPaymentByBv()
                }
            }
        }

        btn_next_step_five.setOnClickListener {
            viewModel.createOrder(viewModel.createOrderPartnerBuilder.build())
        }
    }

    private fun hidePayments() {
        payByBvLayout.visibility = View.GONE
    }

    private fun showPaymentByBv() {
        payByBvLayout.visibility = View.VISIBLE
    }

}
