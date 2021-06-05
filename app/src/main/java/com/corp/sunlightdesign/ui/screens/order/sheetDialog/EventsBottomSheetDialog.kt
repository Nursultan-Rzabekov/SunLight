package com.corp.sunlightdesign.ui.screens.order.sheetDialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.data.source.dataSource.TotalEvent
import com.corp.sunlightdesign.data.source.dataSource.remote.orders.entity.Event
import com.corp.sunlightdesign.utils.getImageUrl
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.events_market_item.*
import kotlinx.android.synthetic.main.repeat_events_bottom_sheet.*

class EventsBottomSheetDialog(
    private val eventInteraction: EventInteraction,
    private var event: Event
) : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.repeat_events_bottom_sheet, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        event_name_tv.text = event.name
        event_description_tv.text = event.description
        child_product_price_tv.text =
            requireContext().getString(R.string.amount_decimal_bv, event.priceChild)
        adult_product_price_tv.text =
            requireContext().getString(R.string.amount_decimal_bv, event.priceAdult)

        Glide.with(requireContext())
            .load(getImageUrl(event.image))
            .centerInside()
            .into(event_iv)


        var adultTv = adultCountTv.text.toString().toInt()
        minus_adult_iv.setOnClickListener {
            if (adultTv > 0) adultTv--
            adultCountTv.text = adultTv.toString()
        }
        plus_adult_iv.setOnClickListener {
            if (adultTv >= 0) adultTv++
            adultCountTv.text = adultTv.toString()
        }

        var childTv = childCountTv.text.toString().toInt()
        minus_child_iv.setOnClickListener {
            if (childTv > 0) childTv--
            childCountTv.text = childTv.toString()
        }
        plus_child_iv.setOnClickListener {
            if (childTv >= 0) childTv++
            childCountTv.text = childTv.toString()

        }

        btn_all_right.setOnClickListener {
            eventInteraction.onEventListSelected(
                TotalEvent(
                    adult = adultTv,
                    child = childTv,
                    eventId = event.id ?: 0,
                    comment = commentEditText.text.toString(),
                    event = event
                )
            )
        }

        cancel_btn.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    interface EventInteraction {
        fun onEventListSelected(totalEvent: TotalEvent)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        bottomSheetDialog.setOnShowListener {
            val bottomSheet = it as BottomSheetDialog
            val parentLayout =
                bottomSheet.findViewById<View>(R.id.design_bottom_sheet)
            parentLayout?.let { layout ->
                val behaviour = BottomSheetBehavior.from(layout)
                setupFullHeight(layout)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return bottomSheetDialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        bottomSheet.layoutParams = layoutParams
    }
}