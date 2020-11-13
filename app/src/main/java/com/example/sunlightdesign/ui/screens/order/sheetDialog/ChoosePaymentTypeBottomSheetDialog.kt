package com.example.sunlightdesign.ui.screens.order.sheetDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.sunlightdesign.R
import com.example.sunlightdesign.utils.showMessage
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.choose_payment_type_bottom_sheet_dialog.*

class ChoosePaymentTypeBottomSheetDialog(
    private val chooseTypeInteraction: ChooseTypeInteraction
) : BottomSheetDialogFragment(), View.OnClickListener {

    private var type : Int = 0

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

    }

    interface ChooseTypeInteraction{
        fun onTypeSelected(type: Int)
    }

    companion object{
        const val TAG = "ModalBottomSheet"
    }

    var k = false
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.cashbox_type_btn -> {
                type = 1

                cashbox_type_btn.iconTint = cashbox_type_btn.getColorState(true)
                cashbox_type_btn.strokeColor = cashbox_type_btn.getColorState(true)

                paybox_type_btn.iconTint = paybox_type_btn.getColorState(false)
                paybox_type_btn.strokeColor = paybox_type_btn.getColorState(false)
                bv_type_btn.iconTint = bv_type_btn.getColorState(false)
                bv_type_btn.strokeColor = bv_type_btn.getColorState(false)
            }
            R.id.paybox_type_btn -> {
                type = 2

                paybox_type_btn.iconTint = paybox_type_btn.getColorState(true)
                paybox_type_btn.strokeColor = paybox_type_btn.getColorState(true)

                cashbox_type_btn.iconTint = cashbox_type_btn.getColorState(false)
                cashbox_type_btn.strokeColor = cashbox_type_btn.getColorState(false)
                bv_type_btn.iconTint = bv_type_btn.getColorState(false)
                bv_type_btn.strokeColor = bv_type_btn.getColorState(false)
            }
            R.id.bv_type_btn -> {
                type = 3

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
}