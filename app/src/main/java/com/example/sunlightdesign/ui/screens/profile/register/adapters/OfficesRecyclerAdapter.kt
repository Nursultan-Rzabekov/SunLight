package com.example.sunlightdesign.ui.screens.profile.register.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private var items = arrayListOf<Office>()

    fun setItems(items: ArrayList<Office>){
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
        holder.bind(item = items[position])
    }

    class OfficeViewHolder constructor(
        itemView: View,
        private val officeSelector: OfficeSelector
    ): RecyclerView.ViewHolder(itemView){
        fun bind(item: Office) = with(itemView){

            itemView.office_name_tv.text = item.office_name
            itemView.office_phonenumber_tv.text = item.phone.toString()
            itemView.office_address_tv.text = item.address
            itemView.office_time_tv.text = item.close_hours

            itemView.setOnClickListener {
                officeSelector.onOfficeSelected(adapterPosition)
            }
        }
    }

    interface OfficeSelector{
        fun onOfficeSelected(id : Int)
    }
}