package com.example.sunlightdesign.ui.screens.wallet.withdraw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.wallets.entity.CurrencyX
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.profile.register.adapters.CustomPopupAdapter
import com.example.sunlightdesign.ui.screens.wallet.WalletViewModel
import com.example.sunlightdesign.ui.screens.wallet.withdraw.dialogs.ChooseOfficeBottomSheetDialog
import com.example.sunlightdesign.ui.screens.wallet.withdraw.dialogs.ChoosePaymentBottomSheetDialog
import kotlinx.android.synthetic.main.account_wallet.*
import kotlinx.android.synthetic.main.account_wallet_history.*
import kotlinx.android.synthetic.main.fragment_wallet.*
import kotlinx.android.synthetic.main.fragment_wallet.progress_bar
import kotlinx.android.synthetic.main.fragment_withdraw.*
import kotlinx.android.synthetic.main.transfer_card_layout.*
import timber.log.Timber
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract as contract

class WithdrawFragment:
    StrongFragment<WalletViewModel>(WalletViewModel::class),
    ChoosePaymentBottomSheetDialog.ChoosePaymentInteraction,
    ChooseOfficeBottomSheetDialog.ChooseOfficeDialogInteraction
{
    private lateinit var currencyAdapter: CustomPopupAdapter<CurrencyX>
    private lateinit var choosePaymentBottomSheetDialog: ChoosePaymentBottomSheetDialog
    private lateinit var chooseOfficeBottomSheetDialog: ChooseOfficeBottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_withdraw, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setListeners()
        configViewModel()

        viewModel.getCurrencyInfo()
        viewModel.getOffices()
    }

    private fun configViewModel(){
        viewModel.apply {
            progress.observe(viewLifecycleOwner, Observer {
                progress_bar.visibility = if(it) View.VISIBLE else View.GONE
            })
            calculateInfo.observe(viewLifecycleOwner, Observer {
                it.currencies?.let { list -> setCurrencies(ArrayList(list)) }
            })

            selectedCurrency.observe(viewLifecycleOwner, Observer {
                bvToCurrencyTextView.text = getString(
                    R.string.bv_to_currency,
                    it.currency_bv_value.toString(),
                    it.currency_code.toString()
                )
                amountConvertedTextView.text = calculateAmount(it).toString()
                currencySymbolTextView.text = it.currency_sign
            })
            officesList.observe(viewLifecycleOwner, Observer {
                it.offices?.let {offices ->
                    chooseOfficeBottomSheetDialog = ChooseOfficeBottomSheetDialog(
                        this@WithdrawFragment, ArrayList(offices))
                }
            })
        }
    }

    private fun setListeners() {
        withdrawMoneyBtn.setOnClickListener {
            showPaymentTypeDialog()
        }

        convertBtn.setOnClickListener {
            viewModel.selectedCurrency.value?.let {
                viewModel.onSelectCurrency(it)
            }
            finalAmountLayout.isVisible = true
        }
    }

    private fun setCurrencies(currencies: ArrayList<CurrencyX>) {
        currencyAdapter = CustomPopupAdapter(
            context = requireContext(),
            items = currencies,
            valueChecker = object : CustomPopupAdapter.ValueChecker<CurrencyX, String>{
                override fun filter(value: CurrencyX, subvalue: String?): Boolean {
                    val v = value.currency_name.toString()
                    if (subvalue == null || subvalue.isBlank())
                        return true
                    return v.toLowerCase(Locale.getDefault()).startsWith(subvalue)
                }

                override fun toString(value: CurrencyX?): String = value?.currency_code.toString()

                override fun toLong(value: CurrencyX?): Long = value?.id?.toLong() ?: -1

            }
        )
        currency_tv.setAdapter(currencyAdapter)
        currency_tv.setOnItemClickListener { parent, view, position, id ->
            val adapter = currency_tv.adapter
            val currency = adapter.getItem(position) as CurrencyX

            viewModel.onSelectCurrency(currency)

            currencyAdapter.callFiltering("")
            Timber.d("currency: $currency")
        }
        if (currencies.isNotEmpty()) {
            currency_tv.selectItem(0)
            viewModel.onSelectCurrency(currencies.first())
        }
    }

    override fun onPaymentWithCashSelected() {
        showOfficeChoiceDialog()
    }

    override fun onPaymentWithCardSelected() {

    }

    override fun onNextBtnPressed(office: Int) {

    }

    private fun showPaymentTypeDialog() {
        choosePaymentBottomSheetDialog = ChoosePaymentBottomSheetDialog(this)
        choosePaymentBottomSheetDialog.show(
            parentFragmentManager,
            ChoosePaymentBottomSheetDialog.TAG
        )
    }

    private fun showOfficeChoiceDialog() {
        chooseOfficeBottomSheetDialog.show(
            parentFragmentManager,
            ChooseOfficeBottomSheetDialog.TAG
        )
    }

    private fun calculateAmount(currency: CurrencyX): Double {
        val amountInBv = amountEditText.text.toString().toDoubleOrNull() ?: return .0

        return currency fromBvToCurrency amountInBv
    }

    private infix fun CurrencyX.fromBvToCurrency(amount: Double): Double {
        if (this.currency_bv_value == null) return .0

        return amount * this.currency_bv_value
    }

    private fun AutoCompleteTextView.selectItem(position: Int) {
        this.setText((this.adapter.getItem(position) as CurrencyX).toString(), false)
    }
}