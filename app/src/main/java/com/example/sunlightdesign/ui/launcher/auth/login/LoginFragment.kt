package com.example.sunlightdesign.ui.launcher.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.launcher.auth.AuthViewModel
import com.example.sunlightdesign.usecase.usercase.authUse.SetLogin
import com.example.sunlightdesign.utils.MaskUtils
import com.example.sunlightdesign.utils.biometric.BiometricUtil
import com.example.sunlightdesign.utils.isPhoneValid
import com.example.sunlightdesign.utils.showToast
import kotlinx.android.synthetic.main.sunlight_login.*
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.watchers.MaskFormatWatcher


class LoginFragment : StrongFragment<AuthViewModel>(AuthViewModel::class),
    BiometricUtil.BiometricAuthenticationCallback,
    BiometricUtil.BiometricHolder {

    private val biometricUtil = BiometricUtil(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceViewState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sunlight_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceViewState: Bundle?) {
        super.onViewCreated(view, savedInstanceViewState)

        welcome_login_as_tv.text = getString(R.string.login_welcome)
    }

    override fun onResume() {
        super.onResume()
        setupMask()
        configViewModel()
        setListeners()
    }

    private fun setListeners() {
        btn_enter.setOnClickListener {
            if (setCheckers()) {
                viewModel.getUseCase(
                    SetLogin(
                        MaskUtils.unMaskValue(
                            MaskUtils.PHONE_MASK,
                            phone_et.text.toString()
                        ), password_et.text.toString()
                    )
                )
            }
        }

        remember_checkbox.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                viewModel.setPhoneAndPassword(phoneNumber = MaskUtils.unMaskValue(
                    MaskUtils.PHONE_MASK,
                    phone_et.text.toString()
                ), password = password_et.text.toString())
            }
            else{
                viewModel.setPhoneAndPassword(phoneNumber = "", password = "")
            }
        }

        biometricOptionTextView.setOnClickListener {
            biometricUtil.authenticateByFingerprint(this)
        }
    }

    private fun configViewModel() {
        viewModel.apply {
            progress.observe(viewLifecycleOwner, Observer {
                progress_bar.visibility = if (it) View.VISIBLE else View.GONE
            })
            phoneNumber.observe(viewLifecycleOwner, Observer { phone ->
                phone_et.setText(
                    MaskUtils.maskValue(
                        mask = MaskUtils.PHONE_MASK, value = phone
                    )
                )
                biometricOptionTextView.text = MaskUtils.maskValue(
                    mask = MaskUtils.PHONE_MASK,
                    value = phone
                )
            })
            password.observe(viewLifecycleOwner, Observer { pass ->
                password_et.setText(pass)
            })
        }

    }

    private fun setCheckers(): Boolean {
        if (!isPhoneValid(phone_et.text.toString())) {
            phone_et.error = getString(R.string.wrong_phone_number)
            return false
        }
        return true
    }

    private fun setupMask() {
        MaskImpl(
            MaskUtils.createSlotsFromMask(
                MaskUtils.PHONE_MASK,
                true
            ),
            true
        ).also {
            it.isHideHardcodedHead = true
            MaskFormatWatcher(it).apply {
                installOn(phone_et)
            }
        }

        phone_et.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                phone_et.hint = getString(R.string.phone_mask_hint)
            } else {
                phone_et.hint = ""
            }
        }
    }

    override fun onBiometricIntent(intent: BiometricUtil.BiometricResponse) =
        when (intent) {

            is BiometricUtil.BiometricResponse.Success -> {
                showToast("Success fingerprint")
                // ToDo authenticate using phonenumber
            }

            is BiometricUtil.BiometricResponse.Error -> {
                showToast("${intent.errorInt}")
            }

            is BiometricUtil.BiometricResponse.Unavailable -> {
                biometricOptionTextView.isVisible = false
            }

            else -> showToast(getString(R.string.try_again))
        }
}
