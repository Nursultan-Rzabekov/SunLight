package com.example.sunlightdesign.ui.screens.profile.register.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Product
import com.example.sunlightdesign.utils.getImageUrl
import kotlinx.android.synthetic.main.products_list_item.view.*

class ProductsRecyclerAdapter(
    private val items: List<Product>,
    private val productsItemSelected: ProductsItemSelected
): RecyclerView.Adapter<ProductsRecyclerAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view =  LayoutInflater.from(parent.context)
            .inflate(R.layout.products_list_item, parent,false)
        return ProductViewHolder(view,productsItemSelected)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) =
        holder.bind(items[position], ::selectItem)

    fun getCheckedProducts(): List<Product> {
        return items.filter { it.isChecked }
    }

    private fun selectItem(position: Int) {
        items.forEach { it.isChecked = false }
        items[position].isChecked = true
        notifyDataSetChanged()
    }

    class ProductViewHolder(
        view: View,
        private val productsItemSelected: ProductsItemSelected
    ): RecyclerView.ViewHolder(view) {
        fun bind(product: Product, onChecked: (Int) -> Unit) {
            itemView.product_name_tv.text = product.product_name
            itemView.product_description_tv.text = product.product_short_description
            itemView.product_price_tv.text = itemView.context.getString(R.string.amount_kzt, product.product_price)

            Glide.with(itemView)
                .load(getImageUrl(product.product_image_front_path))
                .centerCrop()
                .into(itemView.product_iv)

            val itemViewColor = when(product.isChecked){
                false -> ContextCompat.getColorStateList(itemView.context, R.color.transparent)
                true -> ContextCompat.getColorStateList(itemView.context, R.color.colorPrimary)
            }
            itemView.product_card.setStrokeColor(itemViewColor)
            itemView.product_checkbox.isChecked = product.isChecked

            itemView.product_card.setOnClickListener {
                onChecked(adapterPosition)
            }

            itemView.product_more_info_tv.setOnClickListener {
                productsItemSelected.onProductsSelected(product)
            }

        }
    }

    interface ProductsItemSelected{
        fun onProductsSelected(product: Product)
    }
}
