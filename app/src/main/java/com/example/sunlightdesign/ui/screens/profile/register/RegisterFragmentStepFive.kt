package com.example.sunlightdesign.ui.screens.profile.register

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.AddPartnerResponse
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.profile.ProfileViewModel
import kotlinx.android.synthetic.main.bv_payment_card_layout.*
import kotlinx.android.synthetic.main.dialog_notify.*
import kotlinx.android.synthetic.main.fragment_register_partner_step_five.*


class RegisterFragmentStepFive : StrongFragment<ProfileViewModel>(ProfileViewModel::class) {

    companion object {
        private const val PAYMENT_BY_BV = 2
        private const val PAYMENT_BY_TILL = 1
        private const val PAYMENT_BY_PAYBOX = 3
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceViewState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register_partner_step_five, container, false)
    }

    override fun onActivityCreated(savedInstanceViewState: Bundle?) {
        super.onActivityCreated(savedInstanceViewState)

        fillFields()
        setListeners()
        setObservers()
        viewModel.getProfileInfo()
        registration_partner_step_five_payment_rg.check(paymentByTillRbtn.id)
    }

    private fun setObservers() {
        viewModel.apply {
            progress.observe(viewLifecycleOwner, Observer { visible ->
                progress_bar.isVisible = visible
            })

            navigationEvent.observe(viewLifecycleOwner, Observer {
                if (it != null &&
                        it is ProfileViewModel.NavigationEvent.NavigateNext &&
                        it.data is AddPartnerResponse?) {
                    if (it.data?.error.isNullOrBlank()) {
                        showSuccessDialog(it.data?.message?.first()?.first().toString())
                    } else {
                        showFailDialog(it.data?.message.toString())
                    }
                    nullifyNavigation()
                }
            })

            profileInfo.observe(viewLifecycleOwner, Observer {
                bv_balance_amount_tv.text = getString(R.string.amount_bv, it.user?.wallet?.main_wallet)
            })
        }
    }

    private fun setListeners() {
        registration_partner_step_five_payment_rg.setOnCheckedChangeListener { _, checkedId ->
            hidePayments()
            when(checkedId) {
                paymentByTillRbtn.id -> {
                    viewModel.createOrderPartnerBuilder.orderPaymentType = PAYMENT_BY_TILL
                }
                paymentByPayboxRbtn.id -> {
                    viewModel.createOrderPartnerBuilder.orderPaymentType = PAYMENT_BY_PAYBOX
                }
                paymentByBvRbtn.id -> {
                    viewModel.createOrderPartnerBuilder.orderPaymentType = PAYMENT_BY_BV
                    showPaymentByBv()
                }
            }
        }

        btn_next_step_five.setOnClickListener {
            viewModel.createOrder(viewModel.createOrderPartnerBuilder.build())
        }

        backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun fillFields() {
        incomeAndOutcomeLayout.isVisible = false
        var totalPrice = .0
        var totalPriceInBv = .0
        viewModel.createOrderPartnerBuilder.products.forEach {
            totalPrice += it.product_price ?: .0
            totalPriceInBv += it.product_price_in_bv ?: .0
        }
        totalAmountToBuyTextView.text =
            getString(R.string.itogo_k_oplate, totalPriceInBv, totalPrice)
    }

    private fun hidePayments() {
        payByBvLayout.visibility = View.GONE
    }

    private fun showPaymentByBv() {
        payByBvLayout.visibility = View.VISIBLE
    }

    private fun showSuccessDialog(message: String) {
        val dialog = Dialog(requireContext(), R.style.FullDialog).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            window?.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.color.transparent))
            setCancelable(false)
            setContentView(R.layout.dialog_notify)
        }
        dialog.notify_desc_tv.text = message
        dialog.notify_icon_iv.setImageDrawable(
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_success))
        dialog.notify_title_tv.text = getString(R.string.greetings_for_registration)

        dialog.notify_ok_btn.setOnClickListener {
            viewModel.withActivity {
                it.finish()
            }
        }
        dialog.show()
    }

    private fun showFailDialog(message: String) {
        val dialog = Dialog(requireContext(), R.style.FullDialog).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            window?.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.color.transparent))
            setCancelable(false)
            setContentView(R.layout.dialog_notify)
        }
        dialog.notify_desc_tv.text = message
        dialog.notify_icon_iv.setImageDrawable(
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_failed))
        dialog.notify_title_tv.text = getString(R.string.oops_message)

        dialog.notify_ok_btn.setOnClickListener {
            viewModel.withActivity {
                it.finish()
            }
        }
        dialog.show()
    }
}
