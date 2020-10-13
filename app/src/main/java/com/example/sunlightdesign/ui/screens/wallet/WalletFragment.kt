package com.example.sunlightdesign.ui.screens.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.base.StrongFragment
import kotlinx.android.synthetic.main.account_wallet.*
import kotlinx.android.synthetic.main.wallet.*


class WalletFragment : StrongFragment<WalletViewModel>(WalletViewModel::class) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.wallet, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        configViewModel()
        viewModel.getWalletInfo()

    }

    private fun configViewModel(){
        viewModel.apply {
            progress.observe(viewLifecycleOwner, Observer {
                progress_bar.visibility = if(it) View.VISIBLE else View.GONE
            })
            walletLiveData.observe(viewLifecycleOwner, Observer {
                bonusWalletTextView.text = it.wallet.main_wallet.toString()
                activityWalletTextView.text = it.wallet.purchase_wallet.toString()
                leftBranchTotalTextView.text = it.user.left_total
                rightBranchTotalTextView.text = it.user.right_total
            })
        }
    }

}
