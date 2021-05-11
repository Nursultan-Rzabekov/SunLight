package com.example.sunlightdesign.ui.screens.profile.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.CreateOrderPartner
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Product
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.order.adapters.ProductsMarketRecyclerAdapter
import com.example.sunlightdesign.ui.screens.order.market.ProductItem
import com.example.sunlightdesign.ui.screens.order.sheetDialog.ChooseDeliveryTypeBottomSheet
import com.example.sunlightdesign.ui.screens.profile.ProfileViewModel
import com.example.sunlightdesign.utils.NumberUtils
import com.example.sunlightdesign.utils.orZero
import com.example.sunlightdesign.utils.showMessage
import kotlinx.android.synthetic.main.fragment_register_partner_step_three.*
import kotlinx.android.synthetic.main.fragment_register_partner_step_three.pay_market_total_tv
import kotlinx.android.synthetic.main.fragment_register_partner_step_three.product_market_quantity_tv
import kotlinx.android.synthetic.main.fragment_register_partner_step_three.products_recycler_view
import kotlinx.android.synthetic.main.market_products_list.*


class RegisterFragmentStepThree : StrongFragment<ProfileViewModel>(ProfileViewModel::class),
    ProductsMarketRecyclerAdapter.ProductsMarketItemSelected,
    ChooseDeliveryTypeBottomSheet.Interaction {

    companion object {

        const val PACKAGE_NAME = "package_name"

        const val PACKAGE_SUM_KZT = "package_sum_kzt"

        const val PACKAGE_SUM_BV = "package_sum_bv"
    }

    private lateinit var productsAdapter: ProductsMarketRecyclerAdapter
    private lateinit var chooseDeliveryTypeBottomSheet: ChooseDeliveryTypeBottomSheet
    private var spanCount = 2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceViewState: Bundle?
    ): View? = inflater
        .inflate(R.layout.fragment_register_partner_step_three, container, false)


    override fun onActivityCreated(savedInstanceViewState: Bundle?) {
        super.onActivityCreated(savedInstanceViewState)

        arguments?.let {
            available_products_from_tv.text = getString(R.string.available_products_from,
                it.getString(PACKAGE_NAME))
        }
        setListeners()
        setObservers()

        chooseDeliveryTypeBottomSheet = ChooseDeliveryTypeBottomSheet(this)
    }

    override fun onDeliveryTypeSelected(type: Int) {
        if (type == ChooseDeliveryTypeBottomSheet.DELIVERY_BY_COMPANY) {
            viewModel.createOrderPartnerBuilder.deliveryType =
                CreateOrderPartner.DELIVERY_TYPE_BY_COMPANY
            findNavController().navigate(
                R.id.action_register_fragment_step_three_to_deliveryServiceRegisterStepFourFragment
            )
        } else if (type == ChooseDeliveryTypeBottomSheet.DELIVERY_BY_USER) {
            viewModel.createOrderPartnerBuilder.deliveryType =
                CreateOrderPartner.DELIVERY_TYPE_PICKUP
            findNavController().navigate(R.id.action_stepThreeFragment_to_stepFourFragment)
        }
        hideDeliverTypeDialog()
    }

    private fun setListeners() {
        next_step_three_btn.setOnClickListener {
            if (!checkFields()) return@setOnClickListener
            viewModel.createOrderPartnerBuilder.products = productsAdapter.getCheckedProducts()
            viewModel.createOrderPartnerBuilder.paymentSum = Product.getTotalSum(
                productsAdapter.getCheckedProducts()
            )
            showChooseDeliveryTypeDialog()
        }

        back_step_three_btn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setObservers() = with(viewModel) {
        productsList.observe(viewLifecycleOwner, Observer {
            initRecycler(it ?: listOf())
        })
    }

    private fun initRecycler(items: List<Product>) {
        products_recycler_view.apply {
            productsAdapter = ProductsMarketRecyclerAdapter(
                items = items,
                productsMarketItemSelected = this@RegisterFragmentStepThree
            )
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
                productFrontImage = product.product_image_front_path.toString(),
                specialOffer = null
            )
        )
        findNavController().navigate(R.id.stepThreeFragment_to_market_details_fragment, bundle)
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

    private fun checkFields() : Boolean {
        val thresholdInKzt = arguments?.getDouble(PACKAGE_SUM_KZT, 0.0).orZero()
        val thresholdInBv = arguments?.getDouble(PACKAGE_SUM_BV, 0.0).orZero()
        val totalSum = Product.getTotalSum(productsAdapter.getCheckedProducts())
        val totalSumBv = Product.getTotalSumInBv(productsAdapter.getCheckedProducts())
        val message = when {
            productsAdapter.getCheckedProducts().isEmpty() -> "Выберите продукт"
            thresholdInKzt < totalSum -> "Вы превысили лимит. Доступная вам сумма $thresholdInBv" +
                    " BV или ${NumberUtils.prettifyDouble(thresholdInKzt)} тг"
            thresholdInKzt > totalSum -> {
                val diffKzt = thresholdInKzt - totalSum
                val diffBv = thresholdInBv - totalSumBv
                "Вам нужно выбрать еще товар на сумму $diffBv BV " +
                        "или ${NumberUtils.prettifyDouble(diffKzt)} тг"
            }
            else -> null
        }
        if (message != null) {
            showMessage(
                context = requireContext(),
                message = message
            )
            return false
        }
        return true
    }

    private fun showChooseDeliveryTypeDialog() {
        chooseDeliveryTypeBottomSheet.show(
            parentFragmentManager,
            ChooseDeliveryTypeBottomSheet.TAG
        )
    }

    private fun hideDeliverTypeDialog() {
        chooseDeliveryTypeBottomSheet.dismiss()
    }
}
