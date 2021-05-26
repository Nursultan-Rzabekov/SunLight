package com.corp.sunlightdesign.ui.screens.order.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.data.source.dataSource.remote.orders.entity.Ticket
import com.corp.sunlightdesign.utils.DateUtils
import com.corp.sunlightdesign.utils.getImageUrl
import kotlinx.android.synthetic.main.order_item.view.*
import kotlinx.android.synthetic.main.order_sublist_item.view.*

class TicketsRecyclerAdapter(
    private val context: Context,
    private val ticketsSelector: TicketsSelector
) : RecyclerView.Adapter<TicketsRecyclerAdapter.PackageViewHolder>() {

    private var items = mutableListOf<Ticket>()

    fun setItems(items: MutableList<Ticket>) {
        with(this.items) {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.ticket_item, parent, false)
        return PackageViewHolder(view, ticketsSelector)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PackageViewHolder, position: Int) {
        holder.bind(item = items[position])
    }

    class PackageViewHolder constructor(
        itemView: View,
        private val ticketsSelector: TicketsSelector
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Ticket) = with(itemView) {
            itemView.countChildTextView.visibility = View.VISIBLE
            
            this.orderNumberTextView.text = context.getString(R.string.ticket_number, item.id)
            item.event.startedAt?.let {
                this.dataOrdersTextView.text = context.getString(
                    R.string.created_at,
                    DateUtils.reformatDateString(item.event.startedAt, DateUtils.PATTERN_DD_MM_YYYY)
                )
            }
            this.payTextView.text = context.getString(R.string.payToBV, item.price)

            this.detailTextView.setOnClickListener {
                ticketsSelector.onOrderSelected(item)
            }

            itemView.orderNameTextView.text = item.event.name
            itemView.countTextView.text =
                itemView.context.getString(R.string.count_adult, item.adult)
            itemView.countChildTextView.text =
                itemView.context.getString(R.string.count_child, item.child)

            itemView.statusTextView.text =
                if (item.paid == 0) context.getString(R.string.no_paid) else context.getString(R.string.paid)

            Glide.with(itemView)
                .load(getImageUrl(item.event.image))
                .centerInside()
                .into(itemView.productImageView)

        }
    }

    interface TicketsSelector {
        fun onOrderSelected(ticket: Ticket)
    }
}