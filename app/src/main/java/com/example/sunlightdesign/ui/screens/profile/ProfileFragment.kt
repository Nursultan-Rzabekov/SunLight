package com.example.sunlightdesign.ui.screens.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.sunlightdesign.BuildConfig
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.UserInfo
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.VerifyUser
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
import kotlinx.android.synthetic.main.card_verification_box.commentVerifyTextView
import kotlinx.android.synthetic.main.card_verification_box.passVerificationBtn
import kotlinx.android.synthetic.main.card_verification_box.userVerificationStatusTextView
import kotlinx.android.synthetic.main.fragment_account.progress_bar

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
                    setVerifyInfo(it)
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
            viewModel.profileInfo.value?.user?.referal_link_right
                ?.let(::saveReferralLinkToClipboard)
        }

        shareReferralLinkLeftBtn.setOnClickListener {
            viewModel.profileInfo.value?.user?.referal_link_left
                ?.let(::shareReferralLink)
        }

        copyReferralLinkRightBtn.setOnClickListener {
            viewModel.profileInfo.value?.user?.referal_link_left
                ?.let(::saveReferralLinkToClipboard)
        }

        shareReferralLinkRightBtn.setOnClickListener {
            viewModel.profileInfo.value?.user?.referal_link_right
                ?.let(::shareReferralLink)
        }

        allBtn.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_invitedActivity)
        }

        passVerificationBtn.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_userVerificationActivity2)
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
            userCreatedAtTextView.text = DateUtils.reformatDateString(
                dataText = it,
                toPattern = DateUtils.PATTERN_DD_MM_YYYY,
                fromPattern = DateUtils.PATTERN_FULL_DATE
            )
        }
        userActivityStatusTextView.text = if (info.user?.is_active == ACTIVITY_ACTIVE)
            getString(R.string.active)
        else getString(R.string.active)

        bonusWalletTextView.text = getString(R.string.amount_bv, info.user?.wallet?.main_wallet)
        activityWalletTextView.text =
            getString(R.string.amount_bv, info.user?.wallet?.purchase_wallet)
        leftBranchTotalTextView.text =
            getString(R.string.amountText_bv, info.user?.left_total)
        rightBranchTotalTextView.text =
            getString(R.string.amountText_bv, info.user?.right_total)
        leftBranchTotalWeekTextView.text =
            getString(R.string.amount_bv, info.user?.week_bonus?.left_week ?: 0.0)
        rightBranchTotalWeekTextView.text =
            getString(R.string.amount_bv, info.user?.week_bonus?.right_week ?: 0.0)
        referralLink = "${BuildConfig.REFERAL_LINK}${info.user?.referral_link}"

        usersInStructureTextView.text = (info.user?.childs ?: 0).toString()

        invitedAdapter.setItems(info.children ?: listOf())
    }

    private fun setVerifyInfo(userInfo: UserInfo) {
        userVerificationStatusTextView.text =
            if (userInfo.user?.verifyuser == null) {
                getString(R.string.not_verified)
            } else {
                userInfo.user.verifyuser.status_name
            }
        commentVerifyTextView.text = userInfo.user?.verifyuser?.comment
        passVerificationBtn.isEnabled =
            when (userInfo.user?.verifyuser?.status) {
                VerifyUser.STATUS_WAITING_VERIFICATION,
                VerifyUser.STATUS_VERIFIED -> false
                else -> true
            }
        commentVerifyTextView.visibility =
            when (userInfo.user?.verifyuser?.status) {
                VerifyUser.STATUS_REJECTED -> View.VISIBLE
                else -> View.GONE
            }
        userVerificationStatusTextView.setTextColor(
            ContextCompat.getColor(requireContext(),
            when (userInfo.user?.verifyuser?.status) {
                VerifyUser.STATUS_VERIFIED -> R.color.green
                VerifyUser.STATUS_WAITING_VERIFICATION -> R.color.yellow
                else -> R.color.red
            }
        ))
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
