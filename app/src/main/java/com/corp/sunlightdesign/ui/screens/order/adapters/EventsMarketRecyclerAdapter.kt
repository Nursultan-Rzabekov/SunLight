package com.corp.sunlightdesign.ui.screens.order.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.Product
import com.corp.sunlightdesign.data.source.dataSource.remote.orders.entity.Event
import com.corp.sunlightdesign.utils.getImageUrl
import kotlinx.android.synthetic.main.events_market_item.view.*

class EventsMarketRecyclerAdapter(
    private val items: List<Event>,
    private val eventMarketItemSelected: EventMarketItemSelected
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EventsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.events_market_item, parent, false),
            eventMarketItemSelected
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is EventsViewHolder -> holder.bind(items[position])
        }
    }

    class EventsViewHolder(
        view: View,
        private val eventMarketItemSelected: EventMarketItemSelected
    ) : RecyclerView.ViewHolder(view) {

        fun bind(event: Event) {
            itemView.event_name_tv.text = event.name
            itemView.event_description_tv.text = event.description
            itemView.child_product_price_tv.text =
                itemView.context.getString(R.string.amount_decimal_bv, event.priceChild)
            itemView.adult_product_price_tv.text =
                itemView.context.getString(R.string.amount_decimal_bv, event.priceAdult)

            Glide.with(itemView)
                .load(getImageUrl(event.image))
                .centerInside()
                .into(itemView.event_iv)

            itemView.event_more_info_tv.setOnClickListener {
                eventMarketItemSelected.onEventSelected(event)
            }

            itemView.event_card.setOnClickListener {
                eventMarketItemSelected.setEventState(event)
            }
        }
    }

    interface EventMarketItemSelected {
        fun onEventSelected(event: Event)
        fun setEventState(event: Event)
    }
}
