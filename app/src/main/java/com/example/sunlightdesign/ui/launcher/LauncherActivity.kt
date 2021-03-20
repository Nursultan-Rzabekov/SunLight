package com.example.sunlightdesign.ui.launcher

import android.content.Intent
import android.os.Bundle
import androidx.biometric.BiometricPrompt
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.base.StrongActivity
import com.example.sunlightdesign.ui.launcher.auth.pin.PinVerificationFragmentDialog
import com.example.sunlightdesign.ui.screens.MainActivity
import com.example.sunlightdesign.utils.biometric.BiometricUtil
import com.example.sunlightdesign.utils.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel


class LauncherActivity : StrongActivity(), NavController.OnDestinationChangedListener,
    BiometricUtil.BiometricHolder, BiometricUtil.BiometricAuthenticationCallback,
    PinVerificationFragmentDialog.PinVerificationInteraction {

    override val layoutId: Int
        get() = R.layout.activity_launcher

    val viewModel: LauncherViewModel by viewModel()

    private val biometricUtil by lazy { BiometricUtil(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findNavController(R.id.launcher_nav_host_fragment).addOnDestinationChangedListener(this)
        setObservers()
    }

    private fun setObservers() {
        viewModel.bearerToken.observe(this, Observer {
            if (!it) return@Observer
            val isFingerprintEnabled = viewModel.isFingerprintEnabled() &&
                    biometricUtil.checkFingerprintAccess(this)
            val isPinEnabled = viewModel.isPinEnabled()
            when {
                isFingerprintEnabled -> biometricUtil.authenticateByFingerprint(this)
                isPinEnabled -> authenticateByPinVerification()
                else -> navMainActivity()
            }
        })
    }

    override fun onBiometricIntent(intent: BiometricUtil.BiometricResponse) {
        when (intent) {

            is BiometricUtil.BiometricResponse.Success -> navMainActivity()

            is BiometricUtil.BiometricResponse.Error -> {
                if (intent.errorInt != BiometricPrompt.ERROR_LOCKOUT) return
                authenticateByPinVerification()
            }

            is BiometricUtil.BiometricResponse.Unavailable -> authenticateByPinVerification()

            else -> showToast(getString(R.string.try_again))
        }
    }

    override fun onPinIntent(result: PinVerificationFragmentDialog.PinResult) = when (result) {

        is PinVerificationFragmentDialog.PinResult.Success -> navMainActivity()

        else -> showToast("Failure pin")
    }

    private fun authenticateByPinVerification() {
        val isPinEnabled = viewModel.isPinEnabled()
        if (!isPinEnabled) return showToast("Pin is not enabled")
        val pin = viewModel.pin.value
        if (pin.isNullOrBlank()) return

        val dialog = PinVerificationFragmentDialog(pin, this)
        dialog.show(supportFragmentManager, PinVerificationFragmentDialog.TAG)
    }

    private fun navMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) = Unit
}

















