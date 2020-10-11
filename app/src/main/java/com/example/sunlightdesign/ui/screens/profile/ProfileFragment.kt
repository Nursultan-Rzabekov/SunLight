

package com.example.sunlightdesign.ui.screens.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.UserInfo
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.utils.Constants
import com.example.sunlightdesign.utils.Constants.Companion.ACTIVITY_ACTIVE
import kotlinx.android.synthetic.main.account_base_profile_cardview.*
import kotlinx.android.synthetic.main.account_invited_all.*
import kotlinx.android.synthetic.main.account_mini_profile.*
import kotlinx.android.synthetic.main.account_registration_referral.*
import kotlinx.android.synthetic.main.account_wallet.*
import org.koin.android.ext.android.get


class ProfileFragment : StrongFragment<ProfileViewModel>(ProfileViewModel::class) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceViewState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onActivityCreated(savedInstanceViewState: Bundle?) {
        super.onActivityCreated(savedInstanceViewState)

        setObservers()
        setListeners()
    }

    private fun setObservers() {
        viewModel.apply {
            profileInfo.observe(viewLifecycleOwner, Observer {
                it?.let {
                    setUserInfo(it)
                }
            })
        }
    }

    private fun setListeners() {
        register_partner_btn.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragmentFragment_to_registerFragment)
        }
    }

    private fun initRecycler() {
        profileInvitedRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setUserInfo(info: UserInfo) {
        userFullNameTextView.text = ("${info.user?.last_name} ${info.user?.first_name}")
        userIinTextView.text = info.user?.uuid
        userStatusTextView.text = info.user?.status?.status_name
        Glide.with(this)
            .load(info.user?.document_front_path)
            .into(userAvatarCircleImageView)
        userCreatedAtTextView.text = info.user?.created_at
        userActivityStatusTextView.text = if (info.user?.is_active == ACTIVITY_ACTIVE)
                                                getString(R.string.active)
                                            else getString(R.string.active)

        bonusWalletTextView.text = getString(R.string.amount_bv, info.user?.wallet?.main_wallet)
        activityWalletTextView.text = getString(R.string.amount_bv, info.user?.wallet?.purchase_wallet)
        leftBranchTotalTextView.text = getString(R.string.amount_bv, info.user?.wallet?.left_branch_total)
        rightBranchTotalTextView.text = getString(R.string.amount_bv, info.user?.wallet?.right_branch_total)
    }
}
