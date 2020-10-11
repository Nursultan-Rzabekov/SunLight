

package com.example.sunlightdesign.ui.screens.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.UserInfo
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.profile.adapters.InvitedAdapter
import com.example.sunlightdesign.ui.screens.profile.edit.EditProfileFragment.Companion.USER_INFO
import com.example.sunlightdesign.utils.Constants.Companion.ACTIVITY_ACTIVE
import com.example.sunlightdesign.utils.toShortenedUserInfo
import kotlinx.android.synthetic.main.account_base_profile_cardview.*
import kotlinx.android.synthetic.main.account_invited_all.*
import kotlinx.android.synthetic.main.account_mini_profile.*
import kotlinx.android.synthetic.main.account_registration_referral.*
import kotlinx.android.synthetic.main.account_wallet.*
import kotlinx.android.synthetic.main.fragment_account.*

class ProfileFragment : StrongFragment<ProfileViewModel>(ProfileViewModel::class) {

    private lateinit var invitedAdapter: InvitedAdapter
    private var userInfo: UserInfo? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceViewState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onActivityCreated(savedInstanceViewState: Bundle?) {
        super.onActivityCreated(savedInstanceViewState)

        setListeners()
        initRecycler()
        setObservers()

        viewModel.getProfileInfo()
    }

    private fun setObservers() {
        viewModel.apply {
            progress.observe(viewLifecycleOwner, Observer {
                progress_bar.isVisible = it
            })

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

        editProfileBtn.setOnClickListener {
            val bundle = bundleOf(
                USER_INFO to userInfo.toShortenedUserInfo()
            )
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment, bundle)
        }
    }

    private fun initRecycler() {
        profileInvitedRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            invitedAdapter = InvitedAdapter()
            adapter = invitedAdapter
        }
    }

    private fun setUserInfo(info: UserInfo) {
        userInfo = info
        userFullNameTextView.text = ("${info.user?.last_name} ${info.user?.first_name}")
        userUuidTextView.text = info.user?.uuid
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

        invitedAdapter.setItems(info.children ?: listOf())
    }
}
