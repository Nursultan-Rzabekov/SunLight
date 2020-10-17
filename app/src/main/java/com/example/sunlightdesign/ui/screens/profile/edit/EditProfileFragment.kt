package com.example.sunlightdesign.ui.screens.profile.edit

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.ShortenedUserInfo
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.profile.ProfileViewModel
import com.example.sunlightdesign.usecase.usercase.profileUse.post.ChangePassword
import com.example.sunlightdesign.utils.MaskUtils
import kotlinx.android.synthetic.main.account_base_profile_cardview.*
import kotlinx.android.synthetic.main.dialog_change_password.*
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.toolbar_with_back.*
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

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
        setUserInfo(arguments?.getParcelable(USER_INFO))
        setObservers()
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
            avatarImage.observe(viewLifecycleOwner, Observer {
                userAvatarCircleImageView.setImageURI(it)
            })

            navigationEvent.observe(viewLifecycleOwner, Observer {
                if (it != null &&
                        it is ProfileViewModel.NavigationEvent.NoAction) {
                    dismissPasswordDialog()
                }
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
        birthdayEditText.setText(info?.birthday)
        phone_et.setText(info?.phone)
        country_drop_down_tv.setText(info?.countryName)
        city_drop_down_tv.setText(info?.cityName)
        region_drop_down_tv.setText(info?.regionName)

        Glide.with(this)
            .load(info?.document_front_path)
            .placeholder(R.drawable.test_document)
            .error(R.drawable.test_document)
            .into(document_rear_side_iv)

        Glide.with(this)
            .load(info?.document_back_path)
            .placeholder(R.drawable.test_document)
            .error(R.drawable.test_document)
            .into(document_back_side_iv)
    }

    private fun showPasswordDialog() {
        passwordDialog.show()
    }

    private fun dismissPasswordDialog() {
        passwordDialog.dismiss()
    }
}