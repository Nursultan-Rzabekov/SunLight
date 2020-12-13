package com.example.sunlightdesign.ui.screens.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.sunlightdesign.BuildConfig
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.UserInfo
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.profile.adapters.InvitedAdapter
import com.example.sunlightdesign.ui.screens.profile.edit.EditProfileFragment.Companion.USER_INFO
import com.example.sunlightdesign.ui.screens.profile.register.RegisterFragmentStepOne.Companion.USER_ID
import com.example.sunlightdesign.utils.Constants.Companion.ACTIVITY_ACTIVE
import com.example.sunlightdesign.utils.DateUtils
import com.example.sunlightdesign.utils.getImageUrl
import com.example.sunlightdesign.utils.saveToClipboard
import com.example.sunlightdesign.utils.toShortenedUserInfo
import kotlinx.android.synthetic.main.account_base_profile_cardview.*
import kotlinx.android.synthetic.main.account_invited_all.*
import kotlinx.android.synthetic.main.account_mini_profile.*
import kotlinx.android.synthetic.main.account_referral_link.*
import kotlinx.android.synthetic.main.account_registration_referral.*
import kotlinx.android.synthetic.main.account_wallet.*
import kotlinx.android.synthetic.main.fragment_account.*

class ProfileFragment : StrongFragment<ProfileViewModel>(ProfileViewModel::class) {

    private lateinit var invitedAdapter: InvitedAdapter
    private var referralLink: String? = null

    companion object {
        private const val REFERRAL_TAG = "referral"
    }

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


    }

    override fun onResume() {
        super.onResume()
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
            val bundle = bundleOf(
                USER_ID to viewModel.profileInfo.value?.user?.id
            )
            findNavController().navigate(R.id.action_profileFragmentFragment_to_register_activity, bundle)
        }

        editProfileBtn.setOnClickListener {
            val bundle = bundleOf(
                USER_INFO to viewModel.profileInfo.value.toShortenedUserInfo()
            )
            findNavController().navigate(R.id.action_profileFragment_to_editProfileActivity, bundle)
        }

        copyReferralLinkLeftBtn.setOnClickListener {
            referralLink?.let { saveReferralLinkToClipboard(it) }
        }

        shareReferralLinkLeftBtn.setOnClickListener {
            referralLink?.let { shareReferralLink(it) }
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
        userFullNameTextView.text = ("${info.user?.last_name} ${info.user?.first_name}")
        userUuidTextView.text = info.user?.uuid
        userStatusTextView.text = info.user?.status?.status_name
        Glide.with(this)
            .load(getImageUrl(info.user?.user_avatar_path))
            .into(userAvatarCircleImageView)
        info.user?.created_at?.let{
            userCreatedAtTextView.text =  DateUtils.reformatDateString(it, DateUtils.PATTERN_DD_MM_YYYY)
        }
        userActivityStatusTextView.text = if (info.user?.is_active == ACTIVITY_ACTIVE)
            getString(R.string.active)
        else getString(R.string.active)

        bonusWalletTextView.text = getString(R.string.amount_bv, info.user?.wallet?.main_wallet)
        activityWalletTextView.text =
            getString(R.string.amount_bv, info.user?.wallet?.purchase_wallet)
        leftBranchTotalTextView.text =
            getString(R.string.amount_bv, info.user?.wallet?.left_branch_total)
        rightBranchTotalTextView.text =
            getString(R.string.amount_bv, info.user?.wallet?.right_branch_total)
        leftBranchTotalTextViewThisWeek.text =
            getString(R.string.amount_bv, info.weekBonus?.first())
        rightBranchTotalTextViewThisWeek.text =
            getString(R.string.amount_bv, info.weekBonus?.get(1))
        referralLink = "${BuildConfig.REFERAL_LINK}${info.user?.referral_link}"

        usersInStructureTextView.text = info.children?.size.toString()

        invitedAdapter.setItems(info.children ?: listOf())
    }

    private fun saveReferralLinkToClipboard(link: String) {
        saveToClipboard(requireContext(), link, REFERRAL_TAG)
        Toast.makeText(requireContext(), "Copied", Toast.LENGTH_SHORT).show()
    }

    private fun shareReferralLink(link: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, link)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}
