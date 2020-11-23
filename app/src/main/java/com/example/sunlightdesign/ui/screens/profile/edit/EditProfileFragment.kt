package com.example.sunlightdesign.ui.screens.profile.edit

import android.app.Dialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.sunlightdesign.BuildConfig
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.ShortenedUserInfo
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.UserInfo
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.profile.ProfileViewModel
import com.example.sunlightdesign.usecase.usercase.profileUse.post.ChangePassword
import com.example.sunlightdesign.utils.DateUtils
import com.example.sunlightdesign.utils.MaskUtils
import com.example.sunlightdesign.utils.getImageUrl
import com.example.sunlightdesign.utils.toShortenedUserInfo
import kotlinx.android.synthetic.main.account_base_profile_cardview.*
import kotlinx.android.synthetic.main.dialog_change_password.*
import kotlinx.android.synthetic.main.footer_user_card.*
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.toolbar_with_back.*
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import timber.log.Timber

class EditProfileFragment : StrongFragment<ProfileViewModel>(ProfileViewModel::class) {

    companion object {
        const val USER_INFO = "user_info"
    }

    private val passwordDialog by lazy {
        Dialog(requireContext(), R.style.FullDialog).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setCancelable(false)
            setContentView(R.layout.dialog_change_password)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_edit_profile, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        userEditAvatarCircleImageView.isVisible = true
        titleTextView.setText(R.string.profile)

        setListeners()
        setMasks()
        setObservers()

        viewModel.getProfileInfo()
    }

    private fun setListeners() {
        backBtn.setOnClickListener { findNavController().navigateUp() }

        userEditAvatarCircleImageView.setOnClickListener {
            viewModel.onAttachDocument()
        }

        passwordEditTextLayout.setStartIconOnClickListener {
            showPasswordDialog()
        }

        passwordDialog.cancelPasswordChangeImageView.setOnClickListener {
            dismissPasswordDialog()
        }

        exitTextView.setOnClickListener {
            viewModel.nullifyData()
            findNavController().navigate(R.id.action_editProfileFragment_to_launcherActivity)
        }

        passwordDialog.changePasswordBtn.setOnClickListener {
            if (passwordDialog.confirmPasswordEditText.text.toString().isBlank()) return@setOnClickListener
            if (passwordDialog.oldPasswordEditText.text.toString().isBlank()) return@setOnClickListener
            if (passwordDialog.newPasswordEditText.text.toString().isBlank()) return@setOnClickListener
            if (passwordDialog.confirmPasswordEditText.text.toString() !=
                passwordDialog.newPasswordEditText.text.toString()) return@setOnClickListener

            viewModel.changePassword(ChangePassword(
                old_password = passwordDialog.oldPasswordEditText.text.toString().trim(),
                password = passwordDialog.newPasswordEditText.text.toString().trim(),
                password_confirmation = passwordDialog.confirmPasswordEditText.text.toString().trim()
            ))
        }
    }

    private fun setObservers() {
        viewModel.apply {
            progress.observe(viewLifecycleOwner, Observer {
                progress_bar.isVisible = it
            })

            avatarImage.observe(viewLifecycleOwner, Observer {
                userAvatarCircleImageView.setImageURI(it)
            })

            navigationEvent.observe(viewLifecycleOwner, Observer {
                if (it != null && it is ProfileViewModel.NavigationEvent.NoAction) {
                    dismissPasswordDialog()
                }
            })

            profileInfo.observe(viewLifecycleOwner, Observer {
                setUserInfo(it.toShortenedUserInfo())
                setSponsorInfo(it)
            })
        }
    }

    private fun setMasks() {
        MaskImpl(
            MaskUtils.createSlotsFromMask(MaskUtils.PHONE_MASK, true), true
        ).also {
            it.isHideHardcodedHead = true
            MaskFormatWatcher(it).apply {
                installOn(phone_et)
            }
        }
    }

    private fun setUserInfo(info: ShortenedUserInfo?) {
        userFullNameTextView.text = info?.fullName
        userUuidTextView.text = info?.uuid
        userStatusTextView.text = info?.status
        info?.birthday?.let {
            birthdayEditText.setText(
                DateUtils.reformatDateString(it, DateUtils.PATTERN_DD_MM_YYYY)
            )
        }
        phone_et.setText(info?.phone)
        country_drop_down_tv.setText(info?.countryName)
        city_drop_down_tv.setText(info?.cityName)
        region_drop_down_tv.setText(info?.regionName)

        Glide.with(this)
            .load(getImageUrl(info?.document_front_path))
            .addListener(object: RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?, model: Any?, target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    document_rear_side_card.isVisible = false
                    documentsLayout.isVisible = document_back_side_card.isVisible
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?, model: Any?, target: Target<Drawable>?,
                    dataSource: DataSource?, isFirstResource: Boolean
                ): Boolean = false

            })
            .into(document_rear_side_iv)

        Glide.with(this)
            .load(getImageUrl(info?.document_back_path))
            .addListener(object: RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?, model: Any?, target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    document_back_side_card.isVisible = false
                    documentsLayout.isVisible = document_rear_side_card.isVisible
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?, model: Any?, target: Target<Drawable>?,
                    dataSource: DataSource?, isFirstResource: Boolean): Boolean = false

            })
            .into(document_back_side_iv)

        Glide.with(this)
            .load(getImageUrl(info?.user_avatar_path))
            .into(userAvatarCircleImageView)
    }

    private fun setSponsorInfo(userInfo: UserInfo) {
        sponsorFullNameTextView.text = ("${userInfo.parent?.first_name} ${userInfo.parent?.last_name}")
        sponsorLoginTextView.text = userInfo.parent?.email
        sponsorStatusTextView.text = userInfo.parent?.status?.status_name
        sponsorUuidTextView.text = userInfo.parent?.uuid

        Glide.with(this)
            .load(getImageUrl(userInfo.parent?.user_avatar_path))
            .into(sponsorAvatarImageView)
    }

    private fun showPasswordDialog() {
        passwordDialog.show()
    }

    private fun dismissPasswordDialog() {
        passwordDialog.dismiss()
    }
}