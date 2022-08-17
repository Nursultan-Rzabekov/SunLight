package com.corp.sunlightdesign.ui.launcher.auth.pin

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.widget.doAfterTextChanged
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.utils.showToast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.pin_code.*

private const val DEFAULT_PIN_LENGTH = 4

class PinSetupFragmentDialog(
    private val interaction: PinSetupInteraction,
    private val enableInterrupt: Boolean = false
): BottomSheetDialogFragment() {

    private var verifyPin: String? = null

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
        pinView.requestFocus()
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
            when (verifyPin) {
                null -> {
                    verifyPin = enteredPin
                    descriptionTextView.text = getString(R.string.repeat_created_pin_code)
                    pinView.setText("")
                }
                enteredPin -> {
                    interaction.onPinEditComplete(enteredPin)
                    dismiss()
                }
                else -> {
                    pinView.setText("")
                    showToast("Введен неправильный ПИН-код")
                }
            }
        }
        closeButton.setOnClickListener {
            dismiss()
            if (enableInterrupt) {
                interaction.onPinSetupInterrupted()
            }
        }
    }

    interface PinSetupInteraction {
        fun onPinEditComplete(pin: String)
        fun onPinSetupInterrupted()
    }

    companion object {
        const val TAG = "pin_setup"
    }
}