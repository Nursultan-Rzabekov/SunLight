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
import kotlinx.android.synthetic.main.account_wallet.*
import kotlinx.android.synthetic.main.account_wallet_history.*
import kotlinx.android.synthetic.main.fragment_wallet.*
import kotlinx.android.synthetic.main.transfer_card_layout.*
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

class WithdrawFragment: StrongFragment<WalletViewModel>(WalletViewModel::class) {

    private lateinit var currencyAdapter: CustomPopupAdapter<CurrencyX>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_withdraw, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        configViewModel()
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
                calculateAmount(it)
            })
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

                override fun toString(value: CurrencyX?): String = value?.currency_name.toString()

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

    private fun calculateAmount(currency: CurrencyX) {

    }

    private fun AutoCompleteTextView.selectItem(position: Int) {
        this.setText((this.adapter.getItem(position) as CurrencyX).toString(), false)
    }
}