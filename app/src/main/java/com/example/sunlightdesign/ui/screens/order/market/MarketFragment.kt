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
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.order.OrderViewModel
import com.example.sunlightdesign.ui.screens.order.adapters.ProductsMarketRecyclerAdapter
import com.example.sunlightdesign.ui.screens.order.sheetDialog.ChoosePaymentTypeBottomSheetDialog
import com.example.sunlightdesign.ui.screens.order.sheetDialog.PAYMENT_BY_BV
import com.example.sunlightdesign.ui.screens.order.sheetDialog.ProductsBottomSheetDialog
import com.example.sunlightdesign.ui.screens.order.sheetDialog.SuccessBottomSheetDialog
import com.example.sunlightdesign.ui.screens.wallet.WalletViewModel
import com.example.sunlightdesign.ui.screens.wallet.withdraw.dialogs.ChooseOfficeBottomSheetDialog
import com.example.sunlightdesign.utils.showMessage
import com.example.sunlightdesign.utils.showToast
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.market_products_list.*
import kotlinx.android.synthetic.main.market_products_list.btn_all_right
import kotlinx.android.synthetic.main.repeat_orders_bottom_sheet.*
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
        product_market_quantity_tv.text = getString(R.string.product_market,0)
        pay_market_total_tv.text = getString(R.string.market_pay, 0.00)

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

    override fun setProductsState() {
        setTotalQuantityAndCount(productsAdapter.getCheckedProducts())
    }

    private fun setTotalQuantityAndCount(products: List<Product>){
        var count = 0.0
        var productSize = 0
        products.forEach {
            it.product_price?.let { price ->
                it.product_quantity?.let {quantity ->
                    count += (price * quantity)
                    productSize += quantity
                }
            }
        }
        product_market_quantity_tv.text = getString(R.string.product_market,productSize)
        pay_market_total_tv.text = getString(R.string.market_pay, count)
    }

    private fun setObservers() {
        viewModel.products.observe(viewLifecycleOwner, Observer {
            initRecycler(it.products ?: listOf())
        })

        viewModel.orderState.observe(viewLifecycleOwner, Observer {
            if (it.isSuccess) {
                successBottomSheetDialog = SuccessBottomSheetDialog(it.orderType)
                successBottomSheetDialog.show(
                    parentFragmentManager,
                    SuccessBottomSheetDialog.TAG
                )
            }
        })

        viewModel.officesList.observe(viewLifecycleOwner, Observer {
            val citiesList = ArrayList<WalletViewModel.ShortenedCity>()
            it.offices?.forEach { office ->
                office?.city?.id ?: return@forEach
                citiesList.add(
                    WalletViewModel.ShortenedCity(
                        office.city.id,
                        office.city.city_name.toString()
                    )
                )
            }
            chooseOfficeBottomSheetDialog = ChooseOfficeBottomSheetDialog(
                this@MarketFragment,
                it.offices as ArrayList<com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Office?>,
                citiesList,
                true
            )
            chooseOfficeBottomSheetDialog.show(
                parentFragmentManager,
                ChooseOfficeBottomSheetDialog.TAG
            )
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
                product_price_kzt = product.product_price.toString(),
                product_info = product.product_description.toString(),
                productBackImage = product.product_image_back_path.toString(),
                productFrontImage = product.product_image_front_path.toString()
            )
        )
        findNavController().navigate(R.id.market_fragment_to_market_details_fragment, bundle)
    }


    override fun onProductsListSelected(product: List<Product>,count:Double) {
        productsBottomSheetDialog.dismiss()

        viewModel.createOrderBuilder.user_id = viewModel.getUserId()?.toInt() ?: -1
        viewModel.createOrderBuilder.products = productsAdapter.getCheckedProducts()
        viewModel.createOrderBuilder.payment_sum = count

        viewModel.getOfficesList()
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

        val mainWallet = viewModel.products.value?.wallet?.main_wallet ?: .0

        var total = .0
        viewModel.createOrderBuilder.products.forEach { product ->
            total += product.product_price_in_bv ?: .0
        }

        if (total > mainWallet && type == PAYMENT_BY_BV) {
            showMessage(requireContext(), message = getString(R.string.not_enough_bv))
        } else {
            viewModel.storeOrder(createOrderPartner = viewModel.createOrderBuilder.build())
        }
    }
}

@Parcelize
data class ProductItem(
    val product_name: String,
    val product_description: String,
    val product_price_bv: String,
    val product_price_kzt: String,
    val product_info: String,
    val productFrontImage: String,
    val productBackImage: String
) : Parcelable