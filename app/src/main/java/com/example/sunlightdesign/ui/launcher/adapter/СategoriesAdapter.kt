package com.example.sunlightdesign.ui.launcher.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.Category
import kotlinx.android.synthetic.main.main_page_item_view.view.*

class СategoriesAdapter constructor(
    private var items: List<Category> = listOf(),
    private val categoryInterface: CategoryInterface
) : RecyclerView.Adapter<СategoriesAdapter.CategoriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.main_page_item_view, parent, false)
        return CategoriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(items[position],categoryInterface)
    }

    override fun getItemCount() = items.size

    class CategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Category,categoryInterface: CategoryInterface) {
            itemView.category_name_btn.text = item.name

            if(item.selected){
                itemView.category_name_btn.setBackgroundColor(ContextCompat.getColor(itemView.context,R.color.colorPrimary))
            }
            itemView.category_name_btn.setOnClickListener {
                itemView.category_name_btn.setBackgroundColor(ContextCompat.getColor(itemView.context,R.color.colorPrimary))
                categoryInterface.onCategorySelected(item)
            }
        }
    }

    interface CategoryInterface{
        fun onCategorySelected(item: Category)
    }

}