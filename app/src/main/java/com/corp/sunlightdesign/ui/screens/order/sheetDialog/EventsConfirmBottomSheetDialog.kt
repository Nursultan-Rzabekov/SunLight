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
import com.corp.sunlightdesign.utils.DateUtils
import com.corp.sunlightdesign.utils.getImageUrl
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.confirm_events_bottom_sheet.*
import kotlinx.android.synthetic.main.new_events_market_item.*

class EventsConfirmBottomSheetDialog(
    private val eventConfirmInteraction: EventConfirmInteraction,
    private val totalEvent: TotalEvent
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
        return inflater.inflate(R.layout.confirm_events_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        event_name_tv.text = totalEvent.event.name
        event_date_tv.text =
            totalEvent.event.startedAt?.let {
                DateUtils.reformatDateString(
                    it,
                    DateUtils.PATTERN_DD_MM_YYYY
                )
            } ?: "Нет"

        event_description_tv.text = totalEvent.event.description
        child_product_price_tv.text = totalEvent.child.toString()
        adult_product_price_tv.text = totalEvent.adult.toString()
        comment_title_tv.text =
            if (totalEvent.comment.isNullOrEmpty()) "Нет комментарий" else totalEvent.comment

        Glide.with(requireContext())
            .load(getImageUrl(totalEvent.event.image))
            .centerInside()
            .into(event_iv)

        btn_all_right.setOnClickListener {
            eventConfirmInteraction.onEventConfirmed()
        }

        cancel_btn.setOnClickListener {
            dismiss()
        }


    }

    interface EventConfirmInteraction {
        fun onEventConfirmed()
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

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}