package com.example.sunlightdesign.ui.screens.profile.register.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Product
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
        holder.bind(items[position])

    fun getCheckedProducts(): List<Product> {
        return items.filter { it.isChecked }
    }

    class ProductViewHolder(
        view: View,
        val productsItemSelected: ProductsItemSelected
    ): RecyclerView.ViewHolder(view) {
        fun bind(product: Product) {
            itemView.product_name_tv.text = product.product_name
            itemView.product_description_tv.text = product.product_short_description
            itemView.product_price_tv.text = itemView.context.getString(R.string.amount_bv, product.product_price?.toDouble())

            Glide.with(itemView)
                .load(product.product_image_front_path)
                .placeholder(R.drawable.product_test)
                .error(R.drawable.product_test)
                .centerCrop()
                .into(itemView.product_iv)

            itemView.product_card.setOnClickListener {
                val itemViewColor = when(itemView.product_checkbox.isChecked){
                    true -> ContextCompat.getColorStateList(itemView.context, R.color.transparent)
                    false -> ContextCompat.getColorStateList(itemView.context, R.color.colorPrimary)
                }

                itemView.product_card.setStrokeColor(itemViewColor)

                product.isChecked = !itemView.product_checkbox.isChecked
                itemView.product_checkbox.isChecked = !itemView.product_checkbox.isChecked
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
