package com.example.sunlightdesign.ui.screens.order.sheetDialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.example.sunlightdesign.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.choose_type_of_devilery_bottom_sheet.*

class ChooseDeliveryTypeBottomSheet(
    private val interaction: Interaction
): BottomSheetDialogFragment() {

    companion object {
        const val TAG = "delivery"
        const val DELIVERY_BY_COMPANY = 0
        const val DELIVERY_BY_USER = 1
    }

    private var type = DELIVERY_BY_COMPANY

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    private fun setListeners() {
        nextBtn.setOnClickListener {
            interaction.onDeliveryTypeSelected(type)
        }

        deliverTypeByUsBtn.setOnClickListener {
            type = DELIVERY_BY_COMPANY
            selectDeliverByCompany()
        }

        deliverTypeByUserBtn.setOnClickListener {
            type = DELIVERY_BY_USER
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