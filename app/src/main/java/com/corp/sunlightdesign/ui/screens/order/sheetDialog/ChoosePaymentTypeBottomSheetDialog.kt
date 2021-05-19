package com.corp.sunlightdesign.ui.screens.order.sheetDialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.utils.showMessage
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.choose_payment_type_bottom_sheet_dialog.*

const val PAYMENT_BY_BV = 2
const val PAYMENT_BY_TILL = 1
const val PAYMENT_BY_PAYBOX = 3

class ChoosePaymentTypeBottomSheetDialog(
    private val chooseTypeInteraction: ChooseTypeInteraction,
    private val isHideTill: Boolean = false
) : BottomSheetDialogFragment(), View.OnClickListener {

    private var type : Int = 0

    companion object{
        const val TAG = "ModalBottomSheet"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.choose_payment_type_bottom_sheet_dialog, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cashbox_type_btn.setOnClickListener(this)
        paybox_type_btn.setOnClickListener(this)
        bv_type_btn.setOnClickListener(this)
        nextBtn.setOnClickListener(this)
        cancelBtn.setOnClickListener(this)

        cashbox_type_btn.visibility = if (isHideTill) View.GONE else View.VISIBLE
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.cashbox_type_btn -> {
                type = PAYMENT_BY_TILL

                cashbox_type_btn.iconTint = cashbox_type_btn.getColorState(true)
                cashbox_type_btn.strokeColor = cashbox_type_btn.getColorState(true)
                cashbox_type_btn.icon = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.check_toggle_btn_selector
                )

                paybox_type_btn.iconTint = paybox_type_btn.getColorState(false)
                paybox_type_btn.strokeColor = paybox_type_btn.getColorState(false)
                bv_type_btn.iconTint = bv_type_btn.getColorState(false)
                bv_type_btn.strokeColor = bv_type_btn.getColorState(false)
            }
            R.id.paybox_type_btn -> {
                type = PAYMENT_BY_PAYBOX

                paybox_type_btn.iconTint = paybox_type_btn.getColorState(true)
                paybox_type_btn.strokeColor = paybox_type_btn.getColorState(true)

                cashbox_type_btn.iconTint = cashbox_type_btn.getColorState(false)
                cashbox_type_btn.strokeColor = cashbox_type_btn.getColorState(false)
                bv_type_btn.iconTint = bv_type_btn.getColorState(false)
                bv_type_btn.strokeColor = bv_type_btn.getColorState(false)
            }
            R.id.bv_type_btn -> {
                type = PAYMENT_BY_BV

                bv_type_btn.iconTint = bv_type_btn.getColorState(true)
                bv_type_btn.strokeColor = bv_type_btn.getColorState(true)


                cashbox_type_btn.iconTint = cashbox_type_btn.getColorState(false)
                cashbox_type_btn.strokeColor = cashbox_type_btn.getColorState(false)
                paybox_type_btn.iconTint = paybox_type_btn.getColorState(false)
                paybox_type_btn.strokeColor = paybox_type_btn.getColorState(false)
            }
            R.id.nextBtn -> {
                when(type){
                    0 -> return showErrorDialog("Выберите тип платежа")
                    else -> chooseTypeInteraction.onTypeSelected(type)
                }
            }
            R.id.cancelBtn -> {
                dismiss()
            }
        }
    }

    private fun MaterialButton.getColorState(value: Boolean) = when(value){
            true -> ContextCompat.getColorStateList(this.context, R.color.sunLightColor)
            false -> ContextCompat.getColorStateList(this.context, R.color.white_gray)
    }

    private fun showErrorDialog(message: String) {
        showMessage(requireContext(), message = message)
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

    interface ChooseTypeInteraction{
        fun onTypeSelected(type: Int)
    }
}