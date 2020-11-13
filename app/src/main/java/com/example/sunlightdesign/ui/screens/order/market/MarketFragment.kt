package com.example.sunlightdesign.ui.screens.order.market

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Product
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.Order
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.order.OrderViewModel
import com.example.sunlightdesign.ui.screens.order.adapters.ProductsMarketRecyclerAdapter
import com.example.sunlightdesign.ui.screens.order.sheetDialog.ChoosePaymentTypeBottomSheetDialog
import com.example.sunlightdesign.ui.screens.order.sheetDialog.ProductsBottomSheetDialog
import com.example.sunlightdesign.ui.screens.order.sheetDialog.RepeatsOrdersBottomSheetDialog
import com.example.sunlightdesign.ui.screens.order.sheetDialog.SuccessBottomSheetDialog
import com.example.sunlightdesign.ui.screens.wallet.withdraw.dialogs.ChooseOfficeBottomSheetDialog
import com.example.sunlightdesign.utils.showToast
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.market_products_list.*
import kotlinx.android.synthetic.main.toolbar_with_back.*

class MarketFragment : StrongFragment<OrderViewModel>(OrderViewModel::class),
    ProductsMarketRecyclerAdapter.ProductsMarketItemSelected,
    ProductsBottomSheetDialog.ProductsInteraction,
    ChooseOfficeBottomSheetDialog.ChooseOfficeDialogInteraction,
    ChoosePaymentTypeBottomSheetDialog.ChooseTypeInteraction {

    private lateinit var productsAdapter: ProductsMarketRecyclerAdapter
    private var spanCount = 2

    private lateinit var productsBottomSheetDialog: ProductsBottomSheetDialog
    private lateinit var chooseOfficeBottomSheetDialog: ChooseOfficeBottomSheetDialog
    private lateinit var choosePaymentTypeBottomSheetDialog: ChoosePaymentTypeBottomSheetDialog
    private lateinit var successBottomSheetDialog: SuccessBottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.market_products_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleTextView.text = getString(R.string.market)

        setListeners()
        setObservers()

        viewModel.getProductList()

    }

    private fun setListeners() {
        btn_all_right.setOnClickListener {
            when(productsAdapter.getCheckedProducts().size){
                0 -> showToast(getString(R.string.choose_products))
                else -> {
                    productsBottomSheetDialog = ProductsBottomSheetDialog(
                        this@MarketFragment,
                        products = productsAdapter.getCheckedProducts()
                    )
                    productsBottomSheetDialog.show(
                        parentFragmentManager,
                        ProductsBottomSheetDialog.TAG
                    )
                }
            }
        }
    }

    private fun setObservers() {
        viewModel.products.observe(viewLifecycleOwner, Observer {
            initRecycler(it.products ?: listOf())
        })

        viewModel.orderState.observe(viewLifecycleOwner, Observer {
            if(it){
                successBottomSheetDialog = SuccessBottomSheetDialog()
                successBottomSheetDialog.show(
                    parentFragmentManager,
                    SuccessBottomSheetDialog.TAG
                )
            }
        })
    }

    private fun initRecycler(items: List<Product>) {
        products_recycler_view.apply {
            productsAdapter = ProductsMarketRecyclerAdapter(items, this@MarketFragment)
            layoutManager = GridLayoutManager(requireContext(), spanCount)
            adapter = productsAdapter
        }
    }

    override fun onProductsSelected(product: Product) {
        val bundle = bundleOf(
            "item" to ProductItem(
                product_name = product.product_name.toString(),
                product_description = product.product_short_description.toString(),
                product_price_bv = product.product_price_in_bv.toString(),
                product_price_kzt = product.product_price_in_currency.toString(),
                product_info = product.product_description.toString()
            )
        )
        findNavController().navigate(R.id.market_fragment_to_market_details_fragment, bundle)
    }


    override fun onProductsListSelected(product: List<Product>) {
        productsBottomSheetDialog.dismiss()

        viewModel.createOrderBuilder.products = productsAdapter.getCheckedProducts()

        chooseOfficeBottomSheetDialog = ChooseOfficeBottomSheetDialog(this@MarketFragment, arrayListOf())
        chooseOfficeBottomSheetDialog.show(
            parentFragmentManager,
            ChooseOfficeBottomSheetDialog.TAG
        )
    }

    override fun onNextBtnPressed(officeId: Int) {
        chooseOfficeBottomSheetDialog.dismiss()
        viewModel.createOrderBuilder.office_id = officeId
        choosePaymentTypeBottomSheetDialog = ChoosePaymentTypeBottomSheetDialog(this@MarketFragment)
        choosePaymentTypeBottomSheetDialog.show(
            parentFragmentManager,
            ChoosePaymentTypeBottomSheetDialog.TAG
        )
    }

    override fun onTypeSelected(type: Int) {
        choosePaymentTypeBottomSheetDialog.dismiss()
        viewModel.createOrderBuilder.order_payment_type = type
        viewModel.storeOrder(createOrderPartner = viewModel.createOrderBuilder.build())
    }
}

@Parcelize
data class ProductItem(
    val product_name: String,
    val product_description: String,
    val product_price_bv: String,
    val product_price_kzt: String,
    val product_info: String
) : Parcelable