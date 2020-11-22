package com.example.sunlightdesign.ui.screens.order.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Product
import kotlinx.android.synthetic.main.order_sublist_item.view.*

class SubProductsRecyclerAdapter(
    private val items: List<Product>,
    private val statusOrder: String
) : RecyclerView.Adapter<SubProductsRecyclerAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_sublist_item, parent, false)
        return ProductViewHolder(view = view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) =
        holder.bind(items[position],statusOrder)

    class ProductViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bind(product: Product, statusOrder: String) {
            itemView.bvCountTextView.text =
                itemView.context.getString(R.string.totalAmountOrders, product.product_price)
            itemView.bvTextView.text =
                itemView.context.getString(R.string.totalAmountOrders, product.product_price)
            itemView.orderNameTextView.text = product.product_name
            itemView.codeTextView.text =
                itemView.context.getString(R.string.code_number, product.pivot?.model_id)
            itemView.countTextView.text =
                itemView.context.getString(R.string.amount, product.product_quantity)
            itemView.statusTextView.text =
                itemView.context.getString(R.string.status_format, statusOrder)

            Glide.with(itemView)
                .load(product.product_image_back_path)
                .centerCrop()
                .into(itemView.productImageView)
        }
    }
}
