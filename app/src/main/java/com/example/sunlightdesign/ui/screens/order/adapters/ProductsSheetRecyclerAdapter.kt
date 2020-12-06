package com.example.sunlightdesign.ui.screens.order.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Product
import com.example.sunlightdesign.utils.getImageUrl
import kotlinx.android.synthetic.main.product_sheet_item.view.*

class ProductsSheetRecyclerAdapter(
    private val items: List<Product>
) : RecyclerView.Adapter<ProductsSheetRecyclerAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_sheet_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) =
        holder.bind(items[position])

    class ProductViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        fun bind(product: Product) {
            itemView.product_name_tv.text = product.product_name
            itemView.product_price_tv.text =
                itemView.context.getString(R.string.amount_bv, product.product_price)

            Glide.with(itemView)
                .load(getImageUrl(product.product_image_front_path))
                .centerCrop()
                .placeholder(R.drawable.product_test)
                .into(itemView.product_iv)
        }
    }
}
