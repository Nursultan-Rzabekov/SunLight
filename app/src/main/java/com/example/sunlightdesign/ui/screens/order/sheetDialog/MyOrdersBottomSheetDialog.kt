package com.example.sunlightdesign.ui.screens.order.sheetDialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.Order
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.my_order_bottom_sheet_dialog.*

class MyOrdersBottomSheetDialog(
    private val replyOrderInteraction: ReplyOrderInteraction,
    private var order: Order
) : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.my_order_bottom_sheet_dialog, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderSheetTextView.text = getString(R.string.order_number,order.id)
        totalAmountSheetTextView.text = getString(R.string.total_amount,order.total_price)
        typePaymentSheetTextView.text = getString(R.string.type_of_payment, order.order_payment_type_value)
        typeSheetTextView.text = getString(R.string.type,order.order_type_value)
        officeSheetTextView.text = getString(R.string.office,order.office?.office_name)
        contactNumberSheetTextView.text = getString(R.string.contact_number,order.office?.phone)
        dateEndSheetTextView.text = getString(R.string.date_end_of_order,order.order_finish_date)


        repeatOrderBtn.setOnClickListener {
            replyOrderInteraction.onReplyOrderSelected(order)
        }

    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    interface ReplyOrderInteraction{
        fun onReplyOrderSelected(order: Order)
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
}