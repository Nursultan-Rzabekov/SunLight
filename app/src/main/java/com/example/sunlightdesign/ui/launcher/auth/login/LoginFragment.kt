package com.example.sunlightdesign.ui.launcher.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.biometric.BiometricPrompt.ERROR_LOCKOUT
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.launcher.auth.AuthViewModel
import com.example.sunlightdesign.ui.launcher.auth.pin.PinSetupFragmentDialog
import com.example.sunlightdesign.ui.launcher.auth.pin.PinVerificationFragmentDialog
import com.example.sunlightdesign.ui.screens.MainActivity
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
    BiometricUtil.BiometricHolder,
    PinVerificationFragmentDialog.PinVerificationInteraction,
    PinSetupFragmentDialog.PinSetupInteraction {

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
        setupVerificationOptions()
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
            if (isChecked) {
                viewModel.setPhoneAndPassword(phoneNumber = MaskUtils.unMaskValue(
                    MaskUtils.PHONE_MASK,
                    phone_et.text.toString()
                ), password = password_et.text.toString())
            } else {
                viewModel.setPhoneAndPassword(phoneNumber = "", password = "")
            }
        }

        biometricOptionTextView.setOnClickListener {
            val isFingerprintEnabled = viewModel
                .sharedUseCase
                .getSharedPreference()
                .isFingerprintEnabled ?: false
            val isPinEnabled = viewModel
                .sharedUseCase
                .getSharedPreference()
                .isPinEnabled ?: false
            when {
                isFingerprintEnabled -> biometricUtil.authenticateByFingerprint(this)
                isPinEnabled -> authenticateByPinVerification()
            }
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
            isLoginSuccess.observe(viewLifecycleOwner, Observer {
                if (!it) return@Observer
                if (biometricUtil.checkFingerprintAccess(this@LoginFragment)) {
                    requestFingerprintEnabling()
                } else {
                    requestPinEnabling()
                }
            })
        }
    }

    private fun setupVerificationOptions() {
        val isFingerprintEnabled = viewModel
            .sharedUseCase
            .getSharedPreference()
            .isFingerprintEnabled ?: false
        val isPinEnabled = viewModel
            .sharedUseCase
            .getSharedPreference()
            .isPinEnabled ?: false
        biometricOptionTextView.isVisible = isFingerprintEnabled || isPinEnabled
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

    override fun onBiometricIntent(intent: BiometricUtil.BiometricResponse) = when (intent) {

        is BiometricUtil.BiometricResponse.Success -> {
            showToast("Success fingerprint")
            // ToDo authenticate using phonenumber
        }

        is BiometricUtil.BiometricResponse.Error -> {
            if (intent.errorInt == ERROR_LOCKOUT) {
                authenticateByPinVerification()
            }
            showToast("${intent.errorInt}")
        }

        is BiometricUtil.BiometricResponse.Unavailable -> {
            authenticateByPinVerification()
        }

        else -> showToast(getString(R.string.try_again))
    }

    private fun authenticateByPinVerification() {
        val isPinEnabled = viewModel
            .sharedUseCase
            .getSharedPreference()
            .isPinEnabled ?: false
        if (!isPinEnabled) return showToast("Pin is not enabled")
        val pin = viewModel.pin.value
        if (pin.isNullOrBlank()) return

        val dialog = PinVerificationFragmentDialog(pin, this)
        dialog.show(parentFragmentManager, PinVerificationFragmentDialog.TAG)
    }

    override fun onPinIntent(result: PinVerificationFragmentDialog.PinResult) = when (result) {
        is PinVerificationFragmentDialog.PinResult.Success -> {
            showToast("Success pin")
        }
        else -> {
            showToast("Failure pin")
        }
    }

    override fun onPinEditComplete(pin: String) {
        viewModel.setPin(pin)
        startMainPage()
    }

    private fun requestFingerprintEnabling() {
        AlertDialog.Builder(requireContext())
            .setTitle("Fingerprint?")
            .setPositiveButton("Yes") { _, _ ->
                viewModel.setIsFingerprintEnabled(true)
            }
            .setNegativeButton("No") { _, _ ->
                viewModel.setIsFingerprintEnabled(false)
            }
            .setOnDismissListener {
                requestPinEnabling()
            }
            .show()
    }

    private fun requestPinEnabling() {
        AlertDialog.Builder(requireContext())
            .setTitle("Pin?")
            .setPositiveButton("Yes") { _, _ ->
                val dialog = PinSetupFragmentDialog(this)
                dialog.show(parentFragmentManager, PinVerificationFragmentDialog.TAG)
            }
            .setNegativeButton("No") { _, _ ->
                viewModel.clearPin()
                startMainPage()
            }
            .show()
    }

    private fun startMainPage() {
        startActivity(Intent(requireContext(), MainActivity::class.java))
    }
}
