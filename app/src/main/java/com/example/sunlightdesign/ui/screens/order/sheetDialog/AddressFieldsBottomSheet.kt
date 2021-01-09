package com.example.sunlightdesign.ui.screens.order.sheetDialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.example.sunlightdesign.R
import com.example.sunlightdesign.utils.showDialog
import com.example.sunlightdesign.utils.showMessage
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.address_fields_bottom_sheet.*

class AddressFieldsBottomSheet(
    private val interaction: Interaction
) : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "address"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme)
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
            }
        }
        return bottomSheetDialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.address_fields_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setListeners() {
        nextBtn.setOnClickListener {
            if (!checkFields()) return@setOnClickListener showMessage(
                requireContext(), message = getString(R.string.fill_all_the_field))

            interaction.onAddressPassed(0, 0, "")
        }

        cancelBtn.setOnClickListener {
            dismiss()
        }
    }

    private fun checkFields(): Boolean {
        return !countryDropDownTextView.text.isNullOrBlank() &&
                !cityDropDownTextView.text.isNullOrBlank() &&
                !streetAddressEditText.text.isNullOrBlank()
    }

    interface Interaction {
        fun onAddressPassed(country: Int, city: Int, address: String)
    }
}