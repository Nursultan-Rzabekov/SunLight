package com.corp.sunlightdesign.ui.screens.order.sheetDialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.Product
import com.corp.sunlightdesign.ui.screens.order.adapters.ProductsSheetRecyclerAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.repeat_orders_bottom_sheet.*

class ProductsBottomSheetDialog(
    private val productsInteraction: ProductsInteraction,
    private var products: List<Product>
) : BottomSheetDialogFragment() {

    private val productsSheetRecyclerAdapter: ProductsSheetRecyclerAdapter by lazy {
        return@lazy ProductsSheetRecyclerAdapter(products)
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

        var totalQuantity = 0
        products.forEach { totalQuantity += it.product_quantity ?: 0 }
        repeatOrdersSheetTextView.text = getString(R.string.products_choose, totalQuantity)
        countOrderTextView.text = getString(R.string.сount_order)

        var count = 0.0
        products.forEach {
            it.product_price?.let { price ->
                it.product_quantity?.let {quantity ->
                    count += (price * quantity)
                }
            }
        }
        amountOrderTextView.text = getString(R.string.totalAmountOrders, count)

        btn_all_right.setOnClickListener {
            productsInteraction.onProductsListSelected(products,count)
        }
        cancel_btn.setOnClickListener {
            dismiss()
        }

    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    interface ProductsInteraction{
        fun onProductsListSelected(product: List<Product>, count:Double)
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