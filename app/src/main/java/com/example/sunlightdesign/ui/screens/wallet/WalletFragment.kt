package com.example.sunlightdesign.ui.screens.wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.wallet.adapters.WalletHistoryAdapter
import kotlinx.android.synthetic.main.account_wallet.*
import kotlinx.android.synthetic.main.account_wallet_history.*
import kotlinx.android.synthetic.main.fragment_wallet.*
import kotlinx.android.synthetic.main.withdraw_money.*


class WalletFragment : StrongFragment<WalletViewModel>(WalletViewModel::class), WalletHistoryAdapter.WalletHistoryInteraction {

    private val walletHistoryAdapter: WalletHistoryAdapter by lazy {
        WalletHistoryAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wallet, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRecyclerView()
        setListeners()
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

                walletHistoriesLayout.isVisible = it.walletHistory.data.isNotEmpty()
                walletHistoryAdapter.submitList(it.walletHistory.data)
            })
        }
    }

    private fun setListeners() {
        withdrawMoneyLayout.setOnClickListener {
            findNavController().navigate(R.id.action_walletFragment_to_withdrawActivity)
        }
    }

    private fun initRecyclerView() {
        walletHistoryRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = walletHistoryAdapter
        }
    }

    override fun onExpand() {

    }

}
