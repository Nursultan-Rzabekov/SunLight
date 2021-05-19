package com.corp.sunlightdesign.ui.launcher.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.ui.base.StrongFragment
import com.corp.sunlightdesign.ui.launcher.auth.AuthViewModel
import com.corp.sunlightdesign.ui.launcher.auth.pin.PinSetupFragmentDialog
import com.corp.sunlightdesign.ui.screens.MainActivity
import com.corp.sunlightdesign.utils.biometric.BiometricUtil
import com.corp.sunlightdesign.utils.views.PhoneInfo
import kotlinx.android.synthetic.main.sunlight_login.*


class LoginFragment : StrongFragment<AuthViewModel>(AuthViewModel::class),
    BiometricUtil.BiometricHolder,
    PinSetupFragmentDialog.PinSetupInteraction {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceViewState: Bundle?
    ): View? = inflater.inflate(R.layout.sunlight_login, container, false)

    override fun onViewCreated(view: View, savedInstanceViewState: Bundle?) {
        super.onViewCreated(view, savedInstanceViewState)

        welcome_login_as_tv.text = getString(R.string.login_welcome)
    }

    override fun onResume() {
        super.onResume()
        configViewModel()
        setListeners()
    }

    private fun setListeners() {
        btn_enter.setOnClickListener {
            if (!setCheckers()) return@setOnClickListener
            phoneView.getPhoneInfo()?.let {
                viewModel.getUseCase(it, password_et.text.toString())
            }
        }

        remember_checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                phoneView.getPhoneInfo()?.let {
                    viewModel.setPhoneAndPassword(
                        phoneNumber = it,
                        password = password_et.text.toString()
                    )
                }
            } else {
                viewModel.setPhoneAndPassword(
                    phoneNumber = PhoneInfo("", "", ""),
                    password = ""
                )
            }
        }
    }

    private fun configViewModel() {
        viewModel.apply {
            progress.observe(viewLifecycleOwner, Observer {
                progress_bar.visibility = if (it) View.VISIBLE else View.GONE
            })
            phoneNumber.observe(viewLifecycleOwner, Observer { phone ->
                phone ?: return@Observer
                phoneView.setPhoneInfo(phone.countryCode, phone.body)
            })
            password.observe(viewLifecycleOwner, Observer { pass ->
                password_et.setText(pass)
            })
            isLoginSuccess.observe(viewLifecycleOwner, Observer {
                if (!it) return@Observer
                requestPinEnabling()
            })
        }
    }

    private fun setCheckers(): Boolean = phoneView.isValid()

    override fun onPinEditComplete(pin: String) {
        viewModel.setPin(pin)
        if (BiometricUtil.checkFingerprintAccess(this@LoginFragment)) {
            requestFingerprintEnabling()
        } else {
            startMainPage()
        }
    }

    override fun onPinSetupInterrupted() {
        viewModel.clearPin()
        viewModel.setIsFingerprintEnabled(false)
        startMainPage()
    }

    private fun requestFingerprintEnabling() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.enable_fingerprint_question)
            .setCancelable(false)
            .setPositiveButton(R.string.text_yes) { _, _ ->
                viewModel.setIsFingerprintEnabled(true)
            }
            .setNegativeButton(R.string.text_no) { _, _ ->
                viewModel.setIsFingerprintEnabled(false)
            }
            .setOnDismissListener {
                startMainPage()
            }
            .show()
    }

    private fun requestPinEnabling() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.enable_pin_question)
            .setCancelable(false)
            .setPositiveButton(R.string.text_yes) { _, _ ->
                val dialog = PinSetupFragmentDialog(this, true)
                dialog.show(parentFragmentManager, PinSetupFragmentDialog.TAG)
            }
            .setNegativeButton(R.string.text_no) { _, _ ->
                viewModel.clearPin()
                viewModel.setIsFingerprintEnabled(false)
                startMainPage()
            }
            .show()
    }

    private fun startMainPage() {
        startActivity(Intent(requireContext(), MainActivity::class.java))
    }
}
