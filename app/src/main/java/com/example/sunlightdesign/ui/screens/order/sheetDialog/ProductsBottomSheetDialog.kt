package com.example.sunlightdesign.ui.screens.order.sheetDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Product
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.Order
import com.example.sunlightdesign.ui.screens.order.adapters.ProductsSheetRecyclerAdapter
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

        repeatOrdersSheetTextView.text = getString(R.string.products_choose, products.size)
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
            productsInteraction.onProductsListSelected(products)
        }
        cancel_btn.setOnClickListener {
            dismiss()
        }

    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    interface ProductsInteraction{
        fun onProductsListSelected(product: List<Product>)
    }
}