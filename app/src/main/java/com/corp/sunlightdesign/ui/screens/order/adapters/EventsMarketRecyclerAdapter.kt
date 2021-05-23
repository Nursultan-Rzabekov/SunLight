package com.corp.sunlightdesign.ui.screens.order.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.Product
import com.corp.sunlightdesign.utils.getImageUrl
import kotlinx.android.synthetic.main.events_market_item.view.*

class EventsMarketRecyclerAdapter(
    private val items: List<Product>,
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

    fun getCheckedProducts(): List<Product> {
        return items.filter { it.isChecked }
    }

    class EventsViewHolder(
        view: View,
        private val eventMarketItemSelected: EventMarketItemSelected
    ) : RecyclerView.ViewHolder(view) {
        fun bind(product: Product) {
            itemView.product_name_tv.text = product.product_name
            itemView.product_description_tv.text = product.product_short_description
            itemView.child_product_price_tv.text =
                itemView.context.getString(R.string.amount_kzt, product.product_price)
            itemView.adult_product_price_tv.text =
                itemView.context.getString(R.string.amount_kzt, product.product_price)

            Glide.with(itemView)
                .load(getImageUrl(product.product_image_front_path))
                .centerInside()
                .into(itemView.product_iv)

            itemView.product_more_info_tv.setOnClickListener {
                eventMarketItemSelected.onEventSelected(product)
            }

            itemView.product_card.setOnClickListener {
                eventMarketItemSelected.setEventState()
            }
        }
    }

    interface EventMarketItemSelected {
        fun onEventSelected(product: Product)
        fun setEventState()
    }
}
