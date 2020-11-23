package com.example.sunlightdesign.ui.screens.order.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ToggleButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Product
import kotlinx.android.synthetic.main.product_market_item.view.*

class ProductsMarketRecyclerAdapter(
    private val items: List<Product>,
    private val productsMarketItemSelected: ProductsMarketItemSelected
): RecyclerView.Adapter<ProductsMarketRecyclerAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view =  LayoutInflater.from(parent.context)
            .inflate(R.layout.product_market_item, parent,false)
        return ProductViewHolder(view,productsMarketItemSelected)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) =
        holder.bind(items[position])

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

            Glide.with(itemView)
                .load(product.product_image_front_path)
                .placeholder(R.drawable.product_test)
                .error(R.drawable.product_test)
                .centerCrop()
                .into(itemView.product_iv)

            var k = itemView.productQuantity.text.toString().toInt()

            itemView.minusBtn.setOnClickListener {
                if(k>0) k--
                if(k==0){
                    itemView.product_checkbox.isChecked = false
                    itemView.product_card.setStrokeColor(itemView.product_checkbox.setColorState())
                }
                sameClick(itemView = itemView, product = product, k = k)
            }
            itemView.plusBtn.setOnClickListener {
                if(k>=0){
                    k++
                    itemView.product_checkbox.isChecked = true
                    itemView.product_card.setStrokeColor(itemView.product_checkbox.setColorState())
                    sameClick(itemView = itemView, product = product, k = k)
                }
            }

            itemView.product_more_info_tv.setOnClickListener {
                productsMarketItemSelected.onProductsSelected(product)
            }
        }

        private fun sameClick(itemView: View, product:Product, k: Int){
            itemView.productQuantity.text = k.toString()
            product.isChecked = itemView.product_checkbox.isChecked
            product.product_quantity = k
        }

        private fun ToggleButton.setColorState()  =  when(this.isChecked){
            true -> { ContextCompat.getColorStateList(this.context, R.color.colorPrimary) }
            false -> { ContextCompat.getColorStateList(this.context, R.color.transparent) }
        }
    }

    interface ProductsMarketItemSelected{
        fun onProductsSelected(product: Product)
    }
}
