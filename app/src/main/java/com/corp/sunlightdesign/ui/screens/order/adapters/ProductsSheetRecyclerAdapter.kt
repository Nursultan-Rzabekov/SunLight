package com.corp.sunlightdesign.ui.screens.order.adapters

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.Product
import com.corp.sunlightdesign.utils.getImageUrl
import kotlinx.android.synthetic.main.product_sheet_item.view.*
import kotlinx.android.synthetic.main.special_product_sheet_item.view.*

class ProductsSheetRecyclerAdapter(
    private val items: List<Product>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val SPECIAL_PRODUCT = 113
        private const val DEFAULT_PRODUCT = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            SPECIAL_PRODUCT ->
                SpecialProductViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.special_product_sheet_item, parent, false))
            else ->
                ProductViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.product_sheet_item, parent, false)
                )
        }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int =
        when (items[position].product_stock) {
            Product.SPECIAL_OFFER -> SPECIAL_PRODUCT
            else -> DEFAULT_PRODUCT
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SpecialProductViewHolder -> holder.bind(items[position])
            is ProductViewHolder -> holder.bind(items[position])
        }
    }

    class ProductViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        fun bind(product: Product) {
            itemView.product_name_tv.text = product.product_name
            itemView.product_price_tv.text =
                itemView.context.getString(R.string.amount_kzt, product.product_price)

            Glide.with(itemView)
                .load(getImageUrl(product.product_image_front_path))
                .centerInside()
                .into(itemView.product_iv)
        }
    }

    class SpecialProductViewHolder(
        view: View
    ): RecyclerView.ViewHolder(view) {

        fun bind(product: Product) {
            val fullText: String = (product.product_name + " " + (product.product_description_sale ?: ""))
            val spannable: Spannable = SpannableString(fullText)
            spannable.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(itemView.context, R.color.red)),
                product.product_name?.length ?: 0,
                (product.product_name + " " + (product.product_description_sale ?: "")).length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            itemView.specialProductTitleTextView.setText(spannable, TextView.BufferType.SPANNABLE)
            itemView.specialProductPriceTextView.text =
                itemView.context.getString(R.string.amount_kzt, product.product_price)

            Glide.with(itemView)
                .load(getImageUrl(product.product_image_sale))
                .centerInside()
                .into(itemView.specialProductAddImageView)

            Glide.with(itemView)
                .load(getImageUrl(product.product_image_front_path))
                .centerInside()
                .into(itemView.specialProductImageView)
        }
    }
}
