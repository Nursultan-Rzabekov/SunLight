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
import com.example.sunlightdesign.ui.screens.order.market.ProductItem
import com.example.sunlightdesign.ui.screens.order.sheetDialog.AddressFieldsBottomSheet
import com.example.sunlightdesign.ui.screens.order.sheetDialog.ChooseDeliveryTypeBottomSheet
import com.example.sunlightdesign.ui.screens.profile.ProfileViewModel
import com.example.sunlightdesign.ui.screens.profile.register.adapters.ProductsRecyclerAdapter
import com.example.sunlightdesign.usecase.usercase.orders.post.StoreDeliveryUseCase
import com.example.sunlightdesign.utils.showMessage
import kotlinx.android.synthetic.main.fragment_register_partner_step_three.*


class RegisterFragmentStepThree : StrongFragment<ProfileViewModel>(ProfileViewModel::class),
    ProductsRecyclerAdapter.ProductsItemSelected,
    ChooseDeliveryTypeBottomSheet.Interaction,
    AddressFieldsBottomSheet.Interaction {

    companion object{
        const val PACKAGE_NAME = "package_name"
    }

    private lateinit var productsAdapter: ProductsRecyclerAdapter
    private lateinit var chooseDeliveryTypeBottomSheet: ChooseDeliveryTypeBottomSheet
    private lateinit var addressFieldsBottomSheet: AddressFieldsBottomSheet
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
        addressFieldsBottomSheet = AddressFieldsBottomSheet(this)
    }

    override fun onDeliveryTypeSelected(type: Int) {
        if (type == ChooseDeliveryTypeBottomSheet.DELIVERY_BY_COMPANY) {
            showAddressFieldsDialog()
            viewModel.createOrderPartnerBuilder.deliveryType =
                CreateOrderPartner.DELIVERY_TYPE_BY_COMPANY
        } else if (type == ChooseDeliveryTypeBottomSheet.DELIVERY_BY_USER) {
            viewModel.createOrderPartnerBuilder.deliveryType =
                CreateOrderPartner.DELIVERY_TYPE_PICKUP
            viewModel.createOrderPartnerBuilder.deliveryId = null
            findNavController().navigate(R.id.action_stepThreeFragment_to_stepFourFragment)
        }
        hideDeliverTypeDialog()
    }

    override fun onAddressPassed(
        partnerFullName: String,
        country: Int,
        region: Int,
        city: Int,
        address: String
    ) {
        hideAddressFieldsDialog()
        viewModel.storeDelivery(
            StoreDeliveryUseCase.DeliverRequest(
            snl = partnerFullName,
            countryId = country,
            regionId = region,
            cityId = city,
            street = address,
            sum = getTotalSum(productsAdapter.getCheckedProducts()).toString()
        ))
    }

    private fun setListeners() {
        next_step_three_btn.setOnClickListener {
            if (!checkFields()) return@setOnClickListener
            viewModel.createOrderPartnerBuilder.products = productsAdapter.getCheckedProducts()
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
        deliverResponse.observe(viewLifecycleOwner, Observer {
            createOrderPartnerBuilder.deliveryId = it.delivery?.id
            findNavController().navigate(
                R.id.action_register_fragment_step_three_to_register_fragment_step_five
            )
        })
        countriesList.observe(viewLifecycleOwner, Observer {
            addressFieldsBottomSheet.setLocations(it)
        })
    }

    private fun initRecycler(items: List<Product>) {
        products_recycler_view.apply {
            productsAdapter = ProductsRecyclerAdapter(items = items,productsItemSelected = this@RegisterFragmentStepThree)
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

    private fun checkFields() : Boolean {
        val message = when {
            productsAdapter.getCheckedProducts().isEmpty() -> "Выберите продукт"
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

    private fun getTotalSum(products: List<Product>): Double {
        var count = 0.0
        products.forEach {
            it.product_price?.let { price ->
                it.product_quantity?.let {quantity ->
                    count += (price * quantity)
                }
            }
        }
        return count
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

    private fun showAddressFieldsDialog() {
        addressFieldsBottomSheet.show(
            parentFragmentManager,
            AddressFieldsBottomSheet.TAG
        )
    }

    private fun hideAddressFieldsDialog() {
        addressFieldsBottomSheet.dismiss()
    }

}
