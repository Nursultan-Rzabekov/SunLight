package com.corp.sunlightdesign.ui.screens.profile.register.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.data.source.dataSource.remote.auth.entity.Package
import com.corp.sunlightdesign.utils.NumberUtils
import kotlinx.android.synthetic.main.partner_packages_list_item.view.*
import timber.log.Timber

class PackageRecyclerAdapter(
    private val context: Context,
    private val packageSelector: PackageSelector
) : RecyclerView.Adapter<PackageRecyclerAdapter.PackageViewHolder>() {

    private var items = arrayListOf<Package>()

    fun setItems(items: ArrayList<Package>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageViewHolder {
        val view =  LayoutInflater.from(context).inflate(R.layout.partner_packages_list_item,parent,false)
        return PackageViewHolder(view, packageSelector)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PackageViewHolder, position: Int) {
        holder.bind(item = items[position], onChecked = ::onChecked)
    }

    private fun onChecked(position: Int) {
        items.forEach { it.isChecked = false }
        items[position].isChecked = true
        notifyDataSetChanged()
    }

    class PackageViewHolder constructor(
        itemView: View,
        private val packageSelector: PackageSelector
    ): RecyclerView.ViewHolder(itemView){
        fun bind(item: Package, onChecked: (position: Int) -> Unit) = with(itemView){
            itemView.package_item_name_radio.isChecked = item.isChecked
            itemView.package_item_name_radio.text = item.package_name
            if (item.isChecked) {
                itemView.package_item_card.setStrokeColor(
                    ContextCompat.getColorStateList(itemView.context, R.color.colorPrimary)
                )
                itemView.package_item_card_name_tv.setBackgroundColor(
                    ContextCompat.getColor(itemView.context, R.color.colorPrimary)
                )
            } else {
                itemView.package_item_card.setStrokeColor(
                    ContextCompat.getColorStateList(itemView.context, R.color.transparent)
                )
                itemView.package_item_card_name_tv.setBackgroundColor(
                    ContextCompat.getColor(itemView.context, R.color.dark_gray)
                )
            }
            itemView.package_item_card_name_tv.text = item.package_description
            item.package_price_in_kzt?.let {
                itemView.package_price_tv.text =
                    itemView.context.getString(R.string.amountText_kzt, NumberUtils.prettifyDouble(it))
            }

            itemView.package_item_card.setOnClickListener {
                Timber.d(adapterPosition.toString())

                packageSelector.onPackageSelected(adapterPosition)
                onChecked(adapterPosition)
            }
        }
    }

    interface PackageSelector{
        fun onPackageSelected(id : Int)
    }
}