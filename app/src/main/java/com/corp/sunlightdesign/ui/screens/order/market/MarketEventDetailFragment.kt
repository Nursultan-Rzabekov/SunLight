package com.corp.sunlightdesign.ui.screens.order.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.data.source.dataSource.remote.orders.entity.Ticket
import com.corp.sunlightdesign.ui.base.StrongFragment
import com.corp.sunlightdesign.ui.screens.order.OrderViewModel
import com.corp.sunlightdesign.utils.DateUtils
import com.corp.sunlightdesign.utils.getImageUrl
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.event_item_details.*
import kotlinx.android.synthetic.main.toolbar_with_back.*


class MarketEventDetailFragment : StrongFragment<OrderViewModel>(OrderViewModel::class) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.event_item_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backBtn.setText(R.string.back)
        backBtn.setOnClickListener { findNavController().navigateUp() }
        titleTextView.setText(R.string.detail_ticket)

        arguments?.getParcelable<Ticket>("item").let {
            product_name_tv.text = it?.event?.name
            date_tv.text =
                it?.event?.startedAt?.let {
                    DateUtils.reformatDateString(
                        it,
                        DateUtils.PATTERN_DD_MM_YYYY
                    )
                } ?: "Нет"

            Glide.with(this)
                .load(getImageUrl(it?.event?.image))
                .centerInside()
                .into(this.eventImageView)

            product_description_tv.text = it?.event?.description
            child_product_price_tv.text = it?.child.toString()
            adult_product_price_tv.text = it?.adult.toString()

            comment_title_tv.text =
                if (it?.commentaryUser.isNullOrEmpty()) "Нет комментарий" else it?.commentaryUser

            status_title_tv.text =
                if (it?.paid == 0) {
                    status_title_tv.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.red
                        )
                    )
                    requireContext().getString(R.string.no_paid)
                } else {
                    status_title_tv.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.green
                        )
                    )
                    requireContext().getString(
                        R.string.paid
                    )
                }

            if (it?.paid == 1) {
                qr_image_view.visibility = View.VISIBLE
                payTextView.visibility = View.GONE
                it.qr?.let { it1 -> getQrGenerator(it1) }
            } else {
                qr_image_view.visibility = View.GONE
                payTextView.visibility = View.VISIBLE
                payTextView.text = requireContext().getString(R.string.payToBV, it?.price)
            }

        }
    }


    private fun getQrGenerator(qrCode: String) {
        val multiFormatWriter = MultiFormatWriter()
        try {
            val bitMatrix = multiFormatWriter.encode(qrCode, BarcodeFormat.QR_CODE, 200, 200);
            val barcodeEncoder = BarcodeEncoder();
            val bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qr_image_view.setImageBitmap(bitmap);
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }
}