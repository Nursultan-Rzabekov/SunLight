package com.corp.sunlightdesign.ui.launcher.auth.pin

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.core.widget.doAfterTextChanged
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.utils.showToast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.pin_code.*

private const val DEFAULT_PIN_LENGTH = 4
private const val MAX_ATTEMPTS = 3

class PinVerificationFragmentDialog(
    private val pin: String,
    private val interaction: PinVerificationInteraction
): BottomSheetDialogFragment() {

    private var attempts: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.pin_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        descriptionTextView.visibility = View.GONE
        setListeners()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        bottomSheetDialog.setOnShowListener {
            val bottomSheet = it as BottomSheetDialog
            val parentLayout =
                bottomSheet.findViewById<View>(R.id.design_bottom_sheet)
            parentLayout?.let { layout ->
                val behaviour = BottomSheetBehavior.from(layout)
                setupFullHeight(layout)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                behaviour.addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback() {
                    override fun onSlide(bottomSheet: View, slideOffset: Float) = Unit

                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                            behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                        }
                    }
                })
            }
        }
        return bottomSheetDialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    private fun setListeners() {
        pinView.doAfterTextChanged {
            val enteredPin = it.toString()
            if (enteredPin.length != DEFAULT_PIN_LENGTH) return@doAfterTextChanged
            if (pin != enteredPin) {
                attempts++
                showToast(getString(R.string.try_again))
                pinView.setText("")
                if (attempts == MAX_ATTEMPTS) {
                    interaction.onPinIntent(PinResult.Failure)
                    dismiss()
                }
            } else {
                interaction.onPinIntent(PinResult.Success)
                dismiss()
            }
        }
        closeButton.setOnClickListener {
            dismiss()
        }
    }

    interface PinVerificationInteraction {
        fun onPinIntent(result: PinResult)
    }

    sealed class PinResult {

        object Success: PinResult()

        object Failure: PinResult()
    }

    companion object {
        const val TAG = "pin_verification"
    }
}