package com.example.sunlightdesign.ui.screens.order.sheetDialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.Order
import com.example.sunlightdesign.ui.screens.order.adapters.ProductsSheetRecyclerAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.repeat_orders_bottom_sheet.*

class RepeatsOrdersBottomSheetDialog(
    private val repeatsOrderInteraction: RepeatsOrderInteraction,
    private var order: Order
) : BottomSheetDialogFragment() {

    private val productsSheetRecyclerAdapter: ProductsSheetRecyclerAdapter by lazy {
        return@lazy ProductsSheetRecyclerAdapter(order.products ?: listOf())
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
        return inflater.inflate(R.layout.repeat_orders_bottom_sheet, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repeatRecyclerView.apply {
            adapter = productsSheetRecyclerAdapter
        }

        repeatOrdersSheetTextView.text = getString(R.string.repeat_orders,order.id)
        amountOrderTextView.text = getString(R.string.totalAmountOrders, order.total_price)

        btn_all_right.setOnClickListener {
            repeatsOrderInteraction.onRepeatsOrderSelected(order)
        }

        cancel_btn.setOnClickListener {
            dismiss()
        }

    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    interface RepeatsOrderInteraction{
        fun onRepeatsOrderSelected(order: Order)
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