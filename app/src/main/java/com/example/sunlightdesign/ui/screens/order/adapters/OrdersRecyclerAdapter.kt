package com.example.sunlightdesign.ui.screens.order.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.orders.entity.Order
import com.example.sunlightdesign.utils.DateUtils
import kotlinx.android.synthetic.main.order_item.view.*

class OrdersRecyclerAdapter(
    private val context: Context,
    private val orderSelector: OrderSelector
) : RecyclerView.Adapter<OrdersRecyclerAdapter.PackageViewHolder>() {

    private var items = mutableListOf<Order>()

    fun setItems(items: MutableList<Order>) {
        with(this.items) {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false)
        return PackageViewHolder(view, orderSelector)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PackageViewHolder, position: Int) {
        holder.bind(item = items[position])
    }

    class PackageViewHolder constructor(
        itemView: View,
        private val orderSelector: OrderSelector
    ) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Order) = with(itemView) {
            this.orderNumberTextView.text = context.getString(R.string.order_number, item.id)
            item.created_at?.let {
                this.dataOrdersTextView.text = context.getString(
                    R.string.created_at,
                    DateUtils.reformatDateString(item.created_at, DateUtils.PATTERN_DD_MM_YYYY)
                )
            }
            this.payTextView.text = context.getString(R.string.payTo, item.total_price)

            if (!item.products.isNullOrEmpty()) {
                val childLayoutManager = LinearLayoutManager(this.productsRecyclerView.context)
                this.productsRecyclerView.apply {
                    layoutManager = childLayoutManager
                    adapter = SubProductsRecyclerAdapter(items = item.products, statusOrder = item.order_status_value.toString())
                    setRecycledViewPool(RecyclerView.RecycledViewPool())
                }
            }

            this.detailTextView.setOnClickListener {
                orderSelector.onOrderSelected(item)
            }
        }
    }

    interface OrderSelector {
        fun onOrderSelected(order: Order)
    }
}