package com.example.sunlightdesign.ui.screens.order.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.Order
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.Product
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
        countOrderTextView.text = getString(R.string.сount_order,order.products?.size)
        amountOrderTextView.text = getString(R.string.totalAmountOrders, order.total_price)

        btn_all_right.setOnClickListener {
            repeatsOrderInteraction.onRepeatsOrderSelected(order)
        }

    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    interface RepeatsOrderInteraction{
        fun onRepeatsOrderSelected(order: Order)
    }
}