package com.corp.sunlightdesign.ui.screens.order.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.Product
import com.corp.sunlightdesign.utils.getImageUrl
import kotlinx.android.synthetic.main.product_market_item.view.*
import kotlinx.android.synthetic.main.special_product_market_item.view.*

class ProductsMarketRecyclerAdapter(
    private val items: List<Product>,
    private val productsMarketItemSelected: ProductsMarketItemSelected
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val PRODUCT_TYPE_UPGRADE = 3
        private const val SPECIAL_PRODUCT = 113
        private const val DEFAULT_PRODUCT = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            SPECIAL_PRODUCT ->
                SpecialProductViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.special_product_market_item, parent,false),
                    productsMarketItemSelected)
            else ->
                ProductViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.product_market_item, parent,false),
                    productsMarketItemSelected)
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (items[position].product_stock) {
            Product.SPECIAL_OFFER -> SPECIAL_PRODUCT
            else -> DEFAULT_PRODUCT
        }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SpecialProductViewHolder -> holder.bind(items[position])
            is ProductViewHolder -> holder.bind(items[position])
        }
    }

    fun getCheckedProducts(): List<Product> {
        return items.filter { it.isChecked }
    }

    class ProductViewHolder(
        view: View,
        private val productsMarketItemSelected: ProductsMarketItemSelected
    ): RecyclerView.ViewHolder(view) {
        fun bind(product: Product) {
            itemView.product_name_tv.text = product.product_name
            itemView.product_description_tv.text = product.product_short_description
            itemView.product_price_tv.text = itemView.context.getString(R.string.amount_kzt, product.product_price)

            if(product.isChecked){
                itemView.productQuantity.text = product.product_quantity.toString()
                itemView.product_checkbox.isChecked = true
                itemView.product_card.setStrokeColor(itemView.product_checkbox.setColorState())
            }

            Glide.with(itemView)
                .load(getImageUrl(product.product_image_front_path))
                .centerInside()
                .into(itemView.product_iv)

            var k = itemView.productQuantity.text.toString().toInt()

            itemView.minusBtn.setOnClickListener {
                if(k > 0) k--
                if(k == 0){
                    itemView.product_checkbox.isChecked = false
                    itemView.product_card.setStrokeColor(itemView.product_checkbox.setColorState())
                }
                sameClick(itemView = itemView, product = product, k = k)
                productsMarketItemSelected.setProductsState()
            }
            itemView.plusBtn.setOnClickListener {
                if (k >= 0) {
                    if (product.product_type != PRODUCT_TYPE_UPGRADE || k != 1) {
                        k++
                    }
                    itemView.product_checkbox.isChecked = true
                    itemView.product_card.setStrokeColor(itemView.product_checkbox.setColorState())
                    sameClick(itemView = itemView, product = product, k = k)
                }
                productsMarketItemSelected.setProductsState()
            }

            itemView.product_more_info_tv.setOnClickListener {
                productsMarketItemSelected.onProductsSelected(product)
            }

            itemView.product_card.setOnClickListener {
                if (k == 0) {
                    k++
                    itemView.product_checkbox.isChecked = true

                } else {
                    k = 0
                    itemView.product_checkbox.isChecked = false
                }
                itemView.product_card.setStrokeColor(itemView.product_checkbox.setColorState())
                sameClick(itemView = itemView, product = product, k = k)
                productsMarketItemSelected.setProductsState()
            }
        }

        private fun sameClick(itemView: View, product: Product, k: Int){
            itemView.productQuantity.text = k.toString()
            product.isChecked = itemView.product_checkbox.isChecked
            product.product_quantity = k
        }

        private fun ToggleButton.setColorState()  =  when(this.isChecked){
            true -> { ContextCompat.getColorStateList(this.context, R.color.colorPrimary) }
            false -> { ContextCompat.getColorStateList(this.context, R.color.transparent) }
        }
    }

    class SpecialProductViewHolder(
        view: View,
        private val productsMarketItemSelected: ProductsMarketItemSelected
    ): RecyclerView.ViewHolder(view) {
        fun bind(product: Product) {
            itemView.special_product_name_tv.text = product.product_name
            itemView.specialAdditionalNameTextView.text = product.product_description_sale
            itemView.special_product_description_tv.text = product.product_short_description
            itemView.special_product_price_tv.text = itemView.context.getString(R.string.amount_kzt, product.product_price)

            Glide.with(itemView)
                .load(getImageUrl(product.product_image_front_path))
                .centerInside()
                .into(itemView.special_product_iv)

            Glide.with(itemView)
                .load(getImageUrl(product.product_image_sale))
                .centerInside()
                .into(itemView.specialProductAdditionalImageView)

            var k = itemView.special_productQuantity.text.toString().toInt()

            itemView.special_minusBtn.setOnClickListener {
                if(k > 0) k--
                if(k == 0){
                    itemView.special_product_checkbox.isChecked = false
                    itemView.special_product_card.setStrokeColor(itemView.special_product_checkbox.setColorState())
                }
                sameClick(itemView = itemView, product = product, k = k)
                productsMarketItemSelected.setProductsState()
            }
            itemView.special_plusBtn.setOnClickListener {
                if (k >= 0) {
                    if (product.product_type != PRODUCT_TYPE_UPGRADE || k != 1) {
                        k++
                    }
                    itemView.special_product_checkbox.isChecked = true
                    itemView.special_product_card.setStrokeColor(itemView.special_product_checkbox.setColorState())
                    sameClick(itemView = itemView, product = product, k = k)
                }
                productsMarketItemSelected.setProductsState()
            }

            itemView.special_product_more_info_tv.setOnClickListener {
                productsMarketItemSelected.onProductsSelected(product)
            }

            itemView.special_product_card.setOnClickListener {
                if (k == 0) {
                    k++
                    itemView.special_product_checkbox.isChecked = true

                } else {
                    k = 0
                    itemView.special_product_checkbox.isChecked = false
                }
                itemView.special_product_card.setStrokeColor(itemView.special_product_checkbox.setColorState())
                sameClick(itemView = itemView, product = product, k = k)
                productsMarketItemSelected.setProductsState()
            }
        }

        private fun sameClick(itemView: View, product: Product, k: Int){
            itemView.special_productQuantity.text = k.toString()
            product.isChecked = itemView.special_product_checkbox.isChecked
            product.product_quantity = k
        }

        private fun ToggleButton.setColorState()  =  when(this.isChecked){
            true -> { ContextCompat.getColorStateList(this.context, R.color.colorPrimary) }
            false -> { ContextCompat.getColorStateList(this.context, R.color.transparent) }
        }
    }

    interface ProductsMarketItemSelected{
        fun onProductsSelected(product: Product)
        fun setProductsState()
    }
}
