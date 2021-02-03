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
import com.example.sunlightdesign.data.source.dataSource.CreateOrderPartner
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Product
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.order.OrderViewModel
import com.example.sunlightdesign.ui.screens.order.adapters.ProductsMarketRecyclerAdapter
import com.example.sunlightdesign.ui.screens.order.sheetDialog.*
import com.example.sunlightdesign.ui.screens.wallet.WalletViewModel
import com.example.sunlightdesign.ui.screens.wallet.withdraw.dialogs.ChooseOfficeBottomSheetDialog
import com.example.sunlightdesign.usecase.usercase.orders.post.StoreDeliveryUseCase
import com.example.sunlightdesign.utils.showMessage
import com.example.sunlightdesign.utils.showToast
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.market_products_list.*
import kotlinx.android.synthetic.main.market_products_list.btn_all_right
import kotlinx.android.synthetic.main.toolbar_with_back.*

class MarketFragment : StrongFragment<OrderViewModel>(OrderViewModel::class),
    ProductsMarketRecyclerAdapter.ProductsMarketItemSelected,
    ProductsBottomSheetDialog.ProductsInteraction,
    ChooseOfficeBottomSheetDialog.ChooseOfficeDialogInteraction,
    ChoosePaymentTypeBottomSheetDialog.ChooseTypeInteraction,
    ChooseDeliveryTypeBottomSheet.Interaction,
    AddressFieldsBottomSheet.Interaction {

    private lateinit var productsAdapter: ProductsMarketRecyclerAdapter
    private var spanCount = 2

    private lateinit var productsBottomSheetDialog: ProductsBottomSheetDialog
    private lateinit var chooseOfficeBottomSheetDialog: ChooseOfficeBottomSheetDialog
    private lateinit var choosePaymentTypeBottomSheetDialog: ChoosePaymentTypeBottomSheetDialog
    private lateinit var successBottomSheetDialog: SuccessBottomSheetDialog
    private lateinit var chooseDeliveryTypeBottomSheet: ChooseDeliveryTypeBottomSheet
    private lateinit var addressFieldsBottomSheet: AddressFieldsBottomSheet

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
        viewModel.getLocations()
        viewModel.getUserInfo()
        chooseDeliveryTypeBottomSheet = ChooseDeliveryTypeBottomSheet(this)
        addressFieldsBottomSheet = AddressFieldsBottomSheet(this)
    }

    private fun setListeners() {
        btn_all_right.setOnClickListener {
            when(productsAdapter.getCheckedProducts().size){
                0 -> showToast(getString(R.string.choose_products))
                else -> {
                    showProductsBottomSheetDialog()
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

    private fun setObservers() = with(viewModel) {
        progress.observe(viewLifecycleOwner, Observer {
            marketProgressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        products.observe(viewLifecycleOwner, Observer {
            initRecycler(it.products ?: listOf())
        })

        orderState.observe(viewLifecycleOwner, Observer {
            if (it.isSuccess) {
                successBottomSheetDialog = SuccessBottomSheetDialog(it.orderType)
                successBottomSheetDialog.show(
                    parentFragmentManager,
                    SuccessBottomSheetDialog.TAG
                )
            }
        })

        locationList.observe(viewLifecycleOwner, Observer {
            addressFieldsBottomSheet.setLocations(it)
        })

        officesList.observe(viewLifecycleOwner, Observer {
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

        deliverResponse.observe(viewLifecycleOwner, Observer {
            createOrderBuilder.deliveryId = it.delivery?.id ?: -1
            showPaymentTypeDialog(isHideTill = true)
        })
    }

    private fun initRecycler(items: List<Product>) {
        products_recycler_view.apply {
            productsAdapter = ProductsMarketRecyclerAdapter(items, this@MarketFragment)
            val manager = GridLayoutManager(requireContext(), spanCount)

            specialProductCase(items, manager)
            layoutManager = manager
            adapter = productsAdapter
        }
    }

    private fun specialProductCase(items: List<Product>, manager: GridLayoutManager) {
        var specialProductCount = 0
        items.forEach { if (it.product_stock == Product.SPECIAL_OFFER) specialProductCount++ }
        manager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when {
                    position < specialProductCount -> spanCount
                    else -> 1
                }
            }
        }
    }

    private fun showProductsBottomSheetDialog() {
        productsBottomSheetDialog = ProductsBottomSheetDialog(
            this@MarketFragment,
            products = productsAdapter.getCheckedProducts()
        )
        productsBottomSheetDialog.show(
            parentFragmentManager,
            ProductsBottomSheetDialog.TAG
        )
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

    private fun showPaymentTypeDialog(isHideTill: Boolean = false) {
        choosePaymentTypeBottomSheetDialog =
            ChoosePaymentTypeBottomSheetDialog(this@MarketFragment, isHideTill)
        choosePaymentTypeBottomSheetDialog.show(
            parentFragmentManager,
            ChoosePaymentTypeBottomSheetDialog.TAG
        )
    }

    override fun onProductsSelected(product: Product) {
        val bundle = bundleOf(
            "item" to ProductItem(
                product_name = product.product_name.toString(),
                product_description = product.product_short_description.toString(),
                product_price_bv = product.product_price_in_bv.toString(),
                product_price_kzt = product.product_price.toString(),
                product_info = product.product_description.toString(),
                productBackImage = product.product_image_back_path,
                productFrontImage = product.product_image_front_path,
                specialOffer =
                    if (product.product_stock == Product.SPECIAL_OFFER) {
                        SpecialOfferProductItem(
                            product.product_image_sale,
                            product.product_description_sale
                        )
                    } else {
                        null
                    }
            )
        )
        findNavController().navigate(R.id.market_fragment_to_market_details_fragment, bundle)
    }


    override fun onProductsListSelected(product: List<Product>,count:Double) {
        productsBottomSheetDialog.dismiss()

        viewModel.createOrderBuilder.userId = viewModel.getUserId()?.toInt() ?: -1
        viewModel.createOrderBuilder.products = productsAdapter.getCheckedProducts()
        viewModel.createOrderBuilder.paymentSum = count

        showChooseDeliveryTypeDialog()
    }

    override fun onNextBtnPressed(officeId: Int) {
        chooseOfficeBottomSheetDialog.dismiss()
        viewModel.createOrderBuilder.officeId = officeId
        showPaymentTypeDialog()
    }

    override fun onTypeSelected(type: Int) {
        choosePaymentTypeBottomSheetDialog.dismiss()
        viewModel.createOrderBuilder.orderPaymentType = type

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

    override fun onDeliveryTypeSelected(type: Int) {
        if (type == ChooseDeliveryTypeBottomSheet.DELIVERY_BY_COMPANY) {
            showAddressFieldsDialog()
            viewModel.createOrderBuilder.deliveryType = CreateOrderPartner.DELIVERY_TYPE_BY_COMPANY
        } else if (type == ChooseDeliveryTypeBottomSheet.DELIVERY_BY_USER) {
            viewModel.getOfficesList()
            viewModel.createOrderBuilder.deliveryType = CreateOrderPartner.DELIVERY_TYPE_BY_USER
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
        val userInfo = viewModel.userInfo.value
        val snl = "${userInfo?.user?.last_name}" +
                " ${userInfo?.user?.first_name} " +
                "${userInfo?.user?.middle_name}"
        viewModel.storeDelivery(StoreDeliveryUseCase.DeliverRequest(
            snl = partnerFullName,
            countryId = country,
            regionId = region,
            cityId = city,
            street = address,
            sum = getTotalSum(productsAdapter.getCheckedProducts()).toString()
        ))
    }
}

@Parcelize
data class ProductItem(
    val product_name: String,
    val product_description: String,
    val product_price_bv: String,
    val product_price_kzt: String,
    val product_info: String,
    val productFrontImage: String?,
    val productBackImage: String?,
    val specialOffer: SpecialOfferProductItem?
) : Parcelable

@Parcelize
data class SpecialOfferProductItem(
    val offerImage: String?,
    val offerDescription: String?
) : Parcelable