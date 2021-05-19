package com.corp.sunlightdesign.ui.screens.profile.register.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.corp.sunlightdesign.R
import kotlinx.android.synthetic.main.item_popup.view.*
import java.util.*

class CustomPopupAdapter<T>(
    private val context: Context,
    private var items: ArrayList<T>?,
    private val valueChecker: ValueChecker<T, String>
) : BaseAdapter(),  Filterable{

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

    override fun getItemId(position: Int): Long = getItemIndex(items?.get(position))

    override fun getFilter(): Filter = mFilter

    fun callFiltering(str: String?) = mFilter.filter(str)

    private fun getItemValue(any: T?): String? {
        return valueChecker.toString(any)
    }

    private fun getItemIndex(any: T?): Long {
        return valueChecker.toLong(any)
    }

    inner class ListFilter : Filter() {
        private val lock = Any()
        override fun performFiltering(prefix: CharSequence?): FilterResults {
            val results = FilterResults()
            if (dataListAllItems == null) {
                synchronized(lock) { dataListAllItems = items }
            }
            val matchValues = ArrayList<T>()
            val searchStrLowerCase = prefix?.toString()?.toLowerCase(Locale.getDefault())
            dataListAllItems?.let{
                for (dataItem in it) {
                    if (valueChecker.filter(dataItem, searchStrLowerCase))
                        matchValues.add(dataItem)
                }
            }
            results.values = matchValues
            results.count = matchValues.size
            return results
        }

        override fun publishResults(
            constraint: CharSequence?,
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

    interface ValueChecker<T, K>{
        fun filter(value: T, subvalue: K?): Boolean
        fun toString(value: T?): String
        fun toLong(value: T?): Long
    }
}
