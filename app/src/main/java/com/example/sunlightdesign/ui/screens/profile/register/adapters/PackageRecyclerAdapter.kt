package com.example.sunlightdesign.ui.screens.profile.register.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Package
import kotlinx.android.synthetic.main.partner_packages_list_item.view.*

class PackageRecyclerAdapter(private val context: Context) : RecyclerView.Adapter<PackageRecyclerAdapter.PackageViewHolder>() {

    private var items = arrayListOf<Package>()

    fun setItems(items: ArrayList<Package>){
        this.items.clear()
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageViewHolder {
        val view =  LayoutInflater.from(context).inflate(R.layout.partner_packages_list_item,parent,false)
        return PackageViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PackageViewHolder, position: Int) {
        holder.bind(item = items[position])
    }

    class PackageViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(item: Package) = with(itemView){
            itemView.package_item_card_name_tv.text = item.package_name
            itemView.package_price_tv.text = item.package_price.toString()
        }
    }
}