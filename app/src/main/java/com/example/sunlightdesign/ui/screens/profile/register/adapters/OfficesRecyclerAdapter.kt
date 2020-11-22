package com.example.sunlightdesign.ui.screens.profile.register.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Office
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.OfficesList
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Package
import kotlinx.android.synthetic.main.office_departments_item.view.*
import kotlinx.android.synthetic.main.partner_packages_list_item.view.*

class OfficesRecyclerAdapter(
    private val context: Context,
    private val officeSelector: OfficeSelector
) : RecyclerView.Adapter<OfficesRecyclerAdapter.OfficeViewHolder>() {

    private var items = arrayListOf<Office?>()

    fun setItems(items: ArrayList<Office?>){
        this.items.clear()
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfficeViewHolder {
        val view =  LayoutInflater.from(context).inflate(R.layout.office_departments_item,parent,false)
        return OfficeViewHolder(view, officeSelector)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: OfficeViewHolder, position: Int) {
        items[position]?.let { holder.bind(item = it, onChecked = ::onChecked) }
    }

    private fun onChecked(position: Int) {
        items.forEach { it?.isChecked = false }
        items[position]?.isChecked = true
        notifyDataSetChanged()
    }

    fun clearSelection(){
        items.forEach { it?.isChecked = false }
        notifyDataSetChanged()
    }

    class OfficeViewHolder constructor(
        itemView: View,
        private val officeSelector: OfficeSelector
    ): RecyclerView.ViewHolder(itemView){
        fun bind(item: Office, onChecked: (position: Int) -> Unit) = with(itemView){
            itemView.office_card_layout.background = if (item.isChecked)
                ContextCompat.getDrawable(itemView.context, R.drawable.card_border_primary_color)
            else ContextCompat.getDrawable(itemView.context, R.drawable.card_border_gray_ec)

            itemView.office_name_tv.text = item.office_name
            itemView.office_phonenumber_tv.text = item.phone.toString()
            itemView.office_address_tv.text = item.address
            itemView.office_time_tv.text = item.close_hours

            itemView.setOnClickListener {
                officeSelector.onOfficeSelected(adapterPosition)
                onChecked(adapterPosition)
            }
        }
    }

    interface OfficeSelector{
        fun onOfficeSelected(id : Int)
    }
}