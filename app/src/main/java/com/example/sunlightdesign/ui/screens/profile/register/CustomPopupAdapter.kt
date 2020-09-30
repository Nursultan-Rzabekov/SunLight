package com.example.sunlightdesign.ui.screens.profile.register

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.ListAdapter
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.City
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Country
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Region
import kotlinx.android.synthetic.main.item_popup.view.*
import java.util.*

class CustomPopupAdapter<T>(
    context: Context,
    private var items: ArrayList<T>?
) : ListAdapter {

    private var mFilter = ListFilter()
    private var dataListAllItems: ArrayList<T>? = null

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_popup, parent, false)

        view.value.text = getItemValue(getItem(position))

        return view
    }

    override fun getCount(): Int = items?.size ?: 0

    override fun getItem(position: Int): T? = items?.get(position)

    override fun getFilter(): Filter = mFilter

    private fun getItemValue(any: T?): String? {
        return when(any){
            is Country -> any.country_name
            is Region -> any.region_name
            is City -> any.city_name
            else -> null
        }
    }

    inner class ListFilter : Filter() {
        private val lock = Any()
        override fun performFiltering(prefix: CharSequence?): FilterResults {
            val results = FilterResults()
            if (dataListAllItems == null) {
                synchronized(lock) { dataListAllItems = items }
            }
            if (prefix == null || prefix.isEmpty()) {
                synchronized(lock) {
                    results.values = dataListAllItems
                    results.count = dataListAllItems?.size ?: 0
                }
            } else {
                val searchStrLowerCase = prefix.toString().toLowerCase(Locale.getDefault())
                val matchValues = ArrayList<T>()
                dataListAllItems?.let {
                    for (dataItem in it) {
                        val value = getItemValue(dataItem) ?: ""
                        if (value.toLowerCase(Locale.getDefault()).startsWith(searchStrLowerCase)) {
                            matchValues.add(dataItem)
                        }
                    }
                }
                results.values = matchValues
                results.count = matchValues.size
            }
            return results
        }

        override fun publishResults(
            constraint: CharSequence,
            results: FilterResults
        ) {
            items = results.values as ArrayList<T>
            if (results.count > 0) {
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }
    }
}
