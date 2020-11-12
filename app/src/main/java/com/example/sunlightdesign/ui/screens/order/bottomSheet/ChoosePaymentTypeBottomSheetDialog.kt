package com.example.sunlightdesign.ui.screens.order.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.content.ContextCompat
import com.example.sunlightdesign.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.choose_payment_type_bottom_sheet_dialog.*
import kotlinx.android.synthetic.main.products_list_item.view.*

class ChoosePaymentTypeBottomSheetDialog(

) : BottomSheetDialogFragment(), View.OnClickListener {

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

    }

    interface ChooseTypeInteraction{
        fun onTypeSelected()
    }

    companion object{
        const val TAG = "ModalBottomSheet"
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.cashbox_type_btn -> {
                cashbox_type_btn.isChecked = !cashbox_type_btn.isChecked
                cashbox_type_btn.strokeColor = getColorState(v.findViewById(R.id.cashbox_type_btn))

            }
            R.id.paybox_type_btn -> {
                paybox_type_btn.isChecked = !paybox_type_btn.isChecked
                paybox_type_btn.strokeColor = getColorState(v.findViewById(R.id.paybox_type_btn))

            }
            R.id.bv_type_btn -> {
                bv_type_btn.isChecked = !bv_type_btn.isChecked
                bv_type_btn.strokeColor = getColorState(v.findViewById(R.id.bv_type_btn))
            }
        }
    }

    private fun getColorState(itemView: MaterialButton) = when(itemView.isChecked){
            false -> ContextCompat.getColorStateList(itemView.context, R.color.transparent)
            true -> ContextCompat.getColorStateList(itemView.context, R.color.colorPrimary)
    }
}