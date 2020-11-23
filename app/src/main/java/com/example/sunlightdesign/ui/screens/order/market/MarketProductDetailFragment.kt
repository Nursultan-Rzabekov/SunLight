package com.example.sunlightdesign.ui.screens.order.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.order.OrderViewModel
import kotlinx.android.synthetic.main.order_item_details.*

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

        arguments?.getParcelable<ProductItem>("item").let {
            product_name_tv.text = it?.product_name
            product_description_tv.text = it?.product_description
            product_price_bv_tv.text = it?.product_price_bv
            product_price_kzt_tv.text = it?.product_price_kzt
            product_info_description_tv.text = it?.product_info
        }

    }
}