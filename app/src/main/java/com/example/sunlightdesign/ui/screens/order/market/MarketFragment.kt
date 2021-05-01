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
import com.example.sunlightdesign.data.source.dataSource.DeliveryInfoRequest
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Product
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.DeliveryServiceResponse
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.bottomsheets.DeliveryAddressBottomSheet
import com.example.sunlightdesign.ui.bottomsheets.DeliveryServiceBottomSheet
import com.example.sunlightdesign.ui.screens.order.OrderViewModel
import com.example.sunlightdesign.ui.screens.order.adapters.ProductsMarketRecyclerAdapter
import com.example.sunlightdesign.ui.screens.order.sheetDialog.*
import com.example.sunlightdesign.ui.screens.wallet.WalletViewModel
import com.example.sunlightdesign.ui.screens.wallet.withdraw.dialogs.ChooseOfficeBottomSheetDialog
import com.example.sunlightdesign.usecase.usercase.orders.CalculateDeliveryUseCase
import com.example.sunlightdesign.usecase.usercase.orders.post.StoreDeliveryUseCase
import com.example.sunlightdesign.utils.orMinusOne
import com.example.sunlightdesign.utils.orZero
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
    DeliveryServiceBottomSheet.Interaction,
    DeliveryAddressBottomSheet.Interaction {

    private lateinit var productsAdapter: ProductsMarketRecyclerAdapter
    private var spanCount = 2

    private lateinit var productsBottomSheetDialog: ProductsBottomSheetDialog
    private lateinit var chooseOfficeBottomSheetDialog: ChooseOfficeBottomSheetDialog
    private lateinit var choosePaymentTypeBottomSheetDialog: ChoosePaymentTypeBottomSheetDialog
    private lateinit var successBottomSheetDialog: SuccessBottomSheetDialog
    private lateinit var chooseDeliveryTypeBottomSheet: ChooseDeliveryTypeBottomSheet
    private lateinit var deliveryServiceBottomSheet: DeliveryServiceBottomSheet
    private lateinit var deliveryAddressBottomSheet: DeliveryAddressBottomSheet

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
        deliveryServiceBottomSheet = DeliveryServiceBottomSheet(this)
        deliveryAddressBottomSheet = DeliveryAddressBottomSheet(this)
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
            deliveryServiceBottomSheet.setLocations(it)
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
        deliveryService.observe(viewLifecycleOwner, Observer {
            deliveryServiceBottomSheet.recycleDeliveryServices(it.deliveryServices.orEmpty())
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

    private fun showDeliveryServiceDialog() {
        deliveryServiceBottomSheet.show(
            parentFragmentManager,
            DeliveryServiceBottomSheet.TAG
        )
    }

    private fun showPaymentTypeDialog(isHideTill: Boolean = false) {
        choosePaymentTypeBottomSheetDialog =
            ChoosePaymentTypeBottomSheetDialog(this@MarketFragment, isHideTill)
        choosePaymentTypeBottomSheetDialog.show(
            parentFragmentManager,
            ChoosePaymentTypeBottomSheetDialog.TAG
        )
    }

    private fun showDeliveryAddressDialog() {
        deliveryAddressBottomSheet.show(
            parentFragmentManager,
            DeliveryAddressBottomSheet.TAG
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
            showDeliveryServiceDialog()
            viewModel.createOrderBuilder.deliveryType = CreateOrderPartner.DELIVERY_TYPE_BY_COMPANY
        } else if (type == ChooseDeliveryTypeBottomSheet.DELIVERY_BY_USER) {
            viewModel.getOfficesList()
            viewModel.createOrderBuilder.deliveryType = CreateOrderPartner.DELIVERY_TYPE_PICKUP
        }
        viewModel.createOrderBuilder.deliveryInfo = null
        hideDeliverTypeDialog()
    }

    override fun onDeliveryServiceSelected(
        cityId: Int,
        countryId: Int,
        regionId: Int,
        deliveryService: DeliveryServiceResponse,
        countryCode: String
    ) {
        deliveryAddressBottomSheet.setDeliveryService(
            cityId = cityId,
            countryId = countryId,
            regionId = regionId,
            deliveryService = deliveryService,
            countryCode = countryCode
        )
        showDeliveryAddressDialog()
    }

    override fun onDeliveryServiceAddressChosen(
        countryId: Int,
        regionId: Int,
        cityId: Int,
        countryCode: String
    ) {
        viewModel.calculateDelivery(CalculateDeliveryUseCase.Request(
            cityId = cityId,
            countryCode = countryCode,
            totalAmount = Product.getTotalSum(productsAdapter.getCheckedProducts()),
            weight = Product.getTotalWeight(productsAdapter.getCheckedProducts()).toString()
        ))
    }

    override fun onDeliveryServiceAddressSelected(
        address: String,
        partner: String,
        cityId: Int,
        countryId: Int,
        countryCode: String,
        regionId: Int,
        deliveryService: DeliveryServiceResponse
    ) {
        viewModel.createOrderBuilder.deliveryInfo = DeliveryInfoRequest(
            delivery_type_id = deliveryService.deliveryTypeId.orMinusOne(),
            delivery_zone_id = deliveryService.deliveryZoneId.orMinusOne(),
            price = deliveryService.price.orZero(),
            weight = Product.getTotalWeight(productsAdapter.getCheckedProducts()).toString(),
            city_id = cityId,
            region_id = regionId,
            country_code = countryCode,
            country_id = countryId,
            address = address,
            fio = partner
        )
        viewModel.createOrderBuilder.paymentSum += deliveryService.price.orZero()
        showPaymentTypeDialog(isHideTill = true)
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