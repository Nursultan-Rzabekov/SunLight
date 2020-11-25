package com.example.sunlightdesign.ui.screens.order.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.order.OrderViewModel
import com.example.sunlightdesign.ui.screens.order.market.adapter.ImageGalleryViewPager
import kotlinx.android.synthetic.main.order_item_details.*
import kotlinx.android.synthetic.main.sunlight_banner.*
import kotlinx.android.synthetic.main.toolbar_with_back.*

class MarketProductDetailFragment: StrongFragment<OrderViewModel>(OrderViewModel::class) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.order_item_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backBtn.setText(R.string.back)

        setListeners()

        arguments?.getParcelable<ProductItem>("item").let {
            product_name_tv.text = it?.product_name
            product_description_tv.text = it?.product_description
            product_price_bv_tv.text = getString(R.string.amountText_bv, it?.product_price_bv)
            product_price_kzt_tv.text = getString(R.string.amountText_kzt, it?.product_price_kzt)
            product_info_description_tv.text = it?.product_info

            it ?: return@let
            initViewPager(listOf(it.productFrontImage, it.productBackImage))
        }
    }

    private fun setListeners() {
        backBtn.setOnClickListener { findNavController().navigateUp() }
    }

    private fun initViewPager(images: List<String>) {
        productImagesViewPager.apply {
            adapter = ImageGalleryViewPager(images)

            imagesDotIndicator.attachViewPager(this)
            imagesDotIndicator.setDotTintRes(R.color.sunBlackColor)
        }
    }
}