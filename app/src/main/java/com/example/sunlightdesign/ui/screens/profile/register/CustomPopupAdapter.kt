package com.example.sunlightdesign.ui.screens.profile.register

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.City
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Country
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Region
import kotlinx.android.synthetic.main.item_popup.view.*

class CustomPopupAdapter(context: Context) : BaseAdapter() {
    private var items = ArrayList<Any>()
    private var layoutInflater = LayoutInflater.from(context)


    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View? {
        var view: View? = p1

        if (view == null) view = layoutInflater.inflate(R.layout.item_popup, p2, false)

        view?.let {
            when(val item = items[p0]){
                is Country -> { it.value.text = item.country_name}
                is Region -> { it.value.text = item.region_name}
                is City -> { it.value.text = item.city_name}
            }
        }
        return view
    }

    override fun getItem(p0: Int): Any = items[p0]
    override fun getItemId(p0: Int): Long = p0.toLong()
    override fun getCount(): Int = items.size

    fun setItems(items: List<Any>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}