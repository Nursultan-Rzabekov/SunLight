package com.corp.sunlightdesign.ui.screens.profile.edit

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.data.source.dataSource.remote.profile.entity.ShortenedUserInfo
import com.corp.sunlightdesign.data.source.dataSource.remote.profile.entity.UserInfo
import com.corp.sunlightdesign.data.source.dataSource.remote.profile.entity.VerifyUser
import com.corp.sunlightdesign.ui.base.StrongFragment
import com.corp.sunlightdesign.ui.launcher.auth.pin.PinSetupFragmentDialog
import com.corp.sunlightdesign.ui.launcher.auth.pin.PinVerificationFragmentDialog
import com.corp.sunlightdesign.ui.screens.profile.ProfileViewModel
import com.corp.sunlightdesign.usecase.usercase.profileUse.post.ChangePassword
import com.corp.sunlightdesign.utils.*
import com.corp.sunlightdesign.utils.biometric.BiometricUtil
import kotlinx.android.synthetic.main.account_base_profile_cardview.*
import kotlinx.android.synthetic.main.card_verification.*
import kotlinx.android.synthetic.main.dialog_change_password.*
import kotlinx.android.synthetic.main.footer_user_card.*
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.toolbar_with_back.*
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

class EditProfileFragment : StrongFragment<ProfileViewModel>(ProfileViewModel::class),
    PinSetupFragmentDialog.PinSetupInteraction,
    BiometricUtil.BiometricHolder,
    BiometricUtil.BiometricAuthenticationCallback {

    companion object {
        const val USER_INFO = "user_info"
    }

    private val isBiometricEnabled by lazy {
        BiometricUtil.checkFingerprintAccess(this)
    }

    private val passwordDialog by lazy {
        Dialog(requireContext(), R.style.FullDialogAnother).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            window?.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.color.transparent
                )
            )
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
        setAuthenticationInfo()
        titleTextView.setText(R.string.profile)

        setListeners()
        setMasks()
        setObservers()

        showUnverifiedUser()

        viewModel.getProfileInfo()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProfileInfo()
    }

    private fun setListeners() {
        backBtn.setOnClickListener { findNavController().navigateUp() }

        userEditAvatarCircleImageView.setOnClickListener {
            if (checkPermission()) {
                viewModel.onAttachDocument(Constants.ACTION_IMAGE_CONTENT_AVATAR_CODE)
            }
        }

        passwordEditTextLayout.setEndIconOnClickListener {
            showPasswordDialog()
        }

        passwordDialog.cancelPasswordChangeImageView.setOnClickListener {
            dismissPasswordDialog()
        }

        exitTextView.setOnClickListener {
            viewModel.nullifyData()
            findNavController().navigate(R.id.action_editProfileFragment_to_launcherActivity)
        }

        passVerificationBtn.setOnClickListener {
            findNavController().navigate(R.id.action_editProfileFragment_to_userVerificationActivity)
        }

        passwordDialog.changePasswordBtn.setOnClickListener {
            if (passwordDialog.confirmPasswordEditText.text.toString()
                    .isBlank()
            ) return@setOnClickListener
            if (passwordDialog.oldPasswordEditText.text.toString()
                    .isBlank()
            ) return@setOnClickListener
            if (passwordDialog.newPasswordEditText.text.toString()
                    .isBlank()
            ) return@setOnClickListener
            if (passwordDialog.confirmPasswordEditText.text.toString() !=
                passwordDialog.newPasswordEditText.text.toString()
            ) return@setOnClickListener

            viewModel.changePassword(
                ChangePassword(
                    old_password = passwordDialog.oldPasswordEditText.text.toString().trim(),
                    password = passwordDialog.newPasswordEditText.text.toString().trim(),
                    password_confirmation = passwordDialog.confirmPasswordEditText.text.toString()
                        .trim()
                )
            )
        }

        enterByPinSwitch.setOnCheckedChangeListener { _, isChecked ->
            changePinTextView.isVisible = isChecked
            viewModel.isPinEnabled = isChecked
            enterByFingerprintSwitch.isChecked = isChecked
            if (!isChecked) return@setOnCheckedChangeListener
            showPinEditor(enableInterrupt = true)
        }

        enterByFingerprintSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (!isBiometricEnabled) return@setOnCheckedChangeListener
            viewModel.isFingerprintEnabled = isChecked
            if (isChecked) {
                enterByPinSwitch.isChecked = true
            }
        }

        changePinTextView.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.sure_change_pin_question)
                .setPositiveButton(R.string.text_yes) { _, _ ->
                    showPinEditor()
                }
                .setNegativeButton(R.string.text_no, null)
                .show()
        }
    }

    private fun setObservers() {
        viewModel.apply {
            progress.observe(viewLifecycleOwner, Observer {
                progress_bar.isVisible = it
            })

            avatarImage.observe(viewLifecycleOwner, Observer {
                Glide.with(this@EditProfileFragment)
                    .load(it)
                    .into(userAvatarCircleImageView)
            })

            navigationEvent.observe(viewLifecycleOwner, Observer {
                if (it != null && it is ProfileViewModel.NavigationEvent.NoAction) {
                    dismissPasswordDialog()
                }
            })

            profileInfo.observe(viewLifecycleOwner, Observer {
                setUserInfo(it.toShortenedUserInfo())
                setSponsorInfo(it)
                setVerifyInfo(it)
            })
        }
    }

    private fun setAuthenticationInfo() {
        fingerprintLayout.isVisible = isBiometricEnabled
        enterByFingerprintSwitch.isChecked = viewModel.isFingerprintEnabled
        enterByPinSwitch.isChecked = viewModel.isPinEnabled
        changePinTextView.isVisible = viewModel.isPinEnabled
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
        repasswordEditText.setText(viewModel.sharedPreferences.editPassword)
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
            .addListener(object : RequestListener<Drawable> {
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
            .addListener(object : RequestListener<Drawable> {
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
                    dataSource: DataSource?, isFirstResource: Boolean
                ): Boolean = false

            })
            .into(document_back_side_iv)

        Glide.with(this)
            .load(getImageUrl(info?.user_avatar_path))
            .into(userAvatarCircleImageView)
    }

    private fun setSponsorInfo(userInfo: UserInfo) {
        sponsorFullNameTextView.text =
            ("${userInfo.parent?.first_name} ${userInfo.parent?.last_name}")
        sponsorStatusTextView.text = userInfo.parent?.status?.status_name
        sponsorUuidTextView.text = userInfo.parent?.uuid

        Glide.with(this)
            .load(getImageUrl(userInfo.parent?.user_avatar_path))
            .into(sponsorAvatarImageView)
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
            ContextCompat.getColor(
                requireContext(),
                when (userInfo.user?.verifyuser?.status) {
                    VerifyUser.STATUS_VERIFIED -> R.color.green
                    VerifyUser.STATUS_WAITING_VERIFICATION -> R.color.yellow
                    else -> R.color.red
                }
            )
        )
    }

    private fun showPasswordDialog() {
        passwordDialog.show()
    }

    private fun dismissPasswordDialog() {
        passwordDialog.dismiss()
    }

    private fun showVerifiedUser() {
        userVerificationStatusTextView.text = getString(R.string.verified)
        passVerificationBtn.visibility = View.GONE
    }

    private fun showUnverifiedUser() {
        userVerificationStatusTextView.text = getString(R.string.not_verified)
        passVerificationBtn.visibility = View.VISIBLE
    }

    private fun showPinEditor(enableInterrupt: Boolean = false) {
        val dialog = PinSetupFragmentDialog(this, enableInterrupt)
        dialog.show(parentFragmentManager, PinVerificationFragmentDialog.TAG)
    }

    override fun onPinEditComplete(pin: String) {
        viewModel.setPin(pin)
    }

    override fun onPinSetupInterrupted() {
        enterByPinSwitch.isChecked = false
    }

    override fun onBiometricIntent(intent: BiometricUtil.BiometricResponse) = Unit

    private fun checkPermission(): Boolean = if (
        requireContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
        PackageManager.PERMISSION_GRANTED &&
        requireContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
        PackageManager.PERMISSION_GRANTED
    ) {
        true
    } else {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            Constants.PERMISSIONS_REQUEST_READ_STORAGE
        )
        false
    }
}