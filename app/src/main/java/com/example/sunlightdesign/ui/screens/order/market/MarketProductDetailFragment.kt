package com.example.sunlightdesign.ui.screens.order.market

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.sunlightdesign.R
import com.example.sunlightdesign.ui.base.StrongFragment
import com.example.sunlightdesign.ui.screens.order.OrderViewModel
import com.example.sunlightdesign.ui.screens.order.market.adapter.ImageGalleryViewPager
import kotlinx.android.synthetic.main.order_item_details.*
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

            val fullText: String = (it?.product_name + " " + (it?.specialOffer?.offerDescription ?: ""))
            val spannable: Spannable = SpannableString(fullText)
            spannable.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.red)),
                it?.product_name?.length ?: 0,
                (it?.product_name + " " + (it?.specialOffer?.offerDescription ?: "")).length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            product_name_tv.setText(spannable, TextView.BufferType.SPANNABLE)
            product_description_tv.text = it?.product_description
            product_price_bv_tv.text = getString(R.string.amountText_bv, it?.product_price_bv)
            product_price_kzt_tv.text = getString(R.string.amountText_kzt, it?.product_price_kzt)
            product_info_description_tv.text = it?.product_info

            it ?: return@let

            val listOfImages = mutableListOf(it.productFrontImage, it.productBackImage)
            if (it.specialOffer != null) {
                listOfImages.add(it.specialOffer.offerImage.toString())
            }
            initViewPager(listOfImages)
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