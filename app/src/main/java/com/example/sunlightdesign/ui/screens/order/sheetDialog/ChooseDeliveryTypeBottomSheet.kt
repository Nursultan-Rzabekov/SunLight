package com.example.sunlightdesign.ui.screens.order.sheetDialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.example.sunlightdesign.R
import com.example.sunlightdesign.utils.showMessage
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.choose_type_of_devilery_bottom_sheet.*

class ChooseDeliveryTypeBottomSheet(
    private val interaction: Interaction
): BottomSheetDialogFragment() {

    companion object {
        const val TAG = "delivery"
        const val DELIVERY_NONE = -1
        const val DELIVERY_BY_COMPANY = 0
        const val DELIVERY_BY_USER = 1
    }

    private var type = DELIVERY_NONE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.choose_type_of_devilery_bottom_sheet, container, false)
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

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        nullifyAll()
    }

    private fun nullifyAll() {
        type = DELIVERY_NONE
        nextBtn.isEnabled = false
        deliverTypeByUsBtn.isChecked = false
        deliverTypeByUserBtn.isChecked = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nullifyAll()
        setListeners()
    }

    private fun setListeners() {
        nextBtn.setOnClickListener {
            if (type == DELIVERY_NONE) return@setOnClickListener showMessage(
                requireContext(),
                message = getString(R.string.fill_all_the_field)
            )
            interaction.onDeliveryTypeSelected(type)
        }

        deliverTypeByUsBtn.setOnClickListener {
            type = DELIVERY_BY_COMPANY
            nextBtn.isEnabled = true
            selectDeliverByCompany()
        }

        deliverTypeByUserBtn.setOnClickListener {
            type = DELIVERY_BY_USER
            nextBtn.isEnabled = true
            selectDeliverByUser()
        }

        cancelBtn.setOnClickListener {
            dismiss()
        }
    }

    private fun selectDeliverByCompany() {
        deliverTypeByUsBtn.isChecked = true
        deliverTypeByUserBtn.isChecked = false
    }

    private fun selectDeliverByUser() {
        deliverTypeByUsBtn.isChecked = false
        deliverTypeByUserBtn.isChecked = true
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        bottomSheet.layoutParams = layoutParams
    }

    interface Interaction {
        fun onDeliveryTypeSelected(type: Int)
    }
}