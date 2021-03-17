package com.example.sunlightdesign.utils.biometric

import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.lang.IllegalArgumentException
import java.util.concurrent.Executor

private const val ILLEGAL_BIOMETRIC_HOLDER =
    "Biometric Holder that you provided are illegal, try using Fragment or AppcompatActivity"

class BiometricUtil(private val callback: BiometricAuthenticationCallback) {

    fun authenticateByFingerprint(holder: BiometricHolder) {
        val context = when (holder) {
            is AppCompatActivity -> holder
            is Fragment -> holder.requireContext()
            else -> throw IllegalArgumentException(ILLEGAL_BIOMETRIC_HOLDER)
        }
        val biometricManager = BiometricManager.from(context)
        val executor = ContextCompat.getMainExecutor(context)
        when (biometricManager.canAuthenticate(BIOMETRIC_WEAK)) {
            BiometricManager.BIOMETRIC_SUCCESS ->
                authenticateByPrompt(executor, holder)
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                callback.onBiometricIntent(BiometricResponse.Unavailable("no hardware"))
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                callback.onBiometricIntent(BiometricResponse.Unavailable("biometric unavailable"))
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                callback.onBiometricIntent(BiometricResponse.Unavailable("biometric not setup"))
            else ->
                callback.onBiometricIntent(BiometricResponse.Unavailable("unknown biometric error"))
        }
    }

    private fun authenticateByPrompt(executor: Executor, holder: BiometricHolder) {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Title")
            .setSubtitle("Subtitle")
            .setDescription("Description")
            .setNegativeButtonText("Cancel")
            .build()

        val biometricCallback = object : BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                callback.onBiometricIntent(BiometricResponse.Success(result))
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                callback.onBiometricIntent(BiometricResponse.Error(errorCode, errString))
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                callback.onBiometricIntent(BiometricResponse.Failed)
            }
        }

        val biometricPrompt = getBiometricPrompt(holder, executor, biometricCallback)
        biometricPrompt.authenticate(promptInfo)
    }

    private fun getBiometricPrompt(
        holder: BiometricHolder,
        executor: Executor,
        callback: BiometricPrompt.AuthenticationCallback
    ) = when (holder) {
        is AppCompatActivity -> BiometricPrompt(holder, executor, callback)
        is Fragment -> BiometricPrompt(holder, executor, callback)
        else -> throw IllegalArgumentException(ILLEGAL_BIOMETRIC_HOLDER)
    }

    interface BiometricHolder

    interface BiometricAuthenticationCallback {
        fun onBiometricIntent(intent: BiometricResponse)
    }

    sealed class BiometricResponse {

        class Success(val result: BiometricPrompt.AuthenticationResult): BiometricResponse()

        object Failed: BiometricResponse()

        class Error(val errorInt: Int, val errString: CharSequence): BiometricResponse()

        class Unavailable(val message: String): BiometricResponse()
    }
}