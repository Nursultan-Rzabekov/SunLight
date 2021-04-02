package com.example.sunlightdesign.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import androidx.appcompat.widget.ListPopupWindow
import com.example.sunlightdesign.R
import kotlinx.android.synthetic.main.item_popup.view.*
import timber.log.Timber
import java.util.*

class BasePopUpAdapter<T>: BaseAdapter() {

    private var items = mutableListOf<T>()

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_popup,
            parent,
            false
        )
        view.value.text = items[position].toString()
        return view
    }

    override fun getItem(position: Int): Any = items[position].toString()

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = items.size

    fun setItems(items: List<T>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}

fun <T> showListPopupWindow(
    context: Context,
    items: List<T>,
    anchor: View,
    adapter: BasePopUpAdapter<T>,
    onSelection: (value: T) -> Unit
): ListPopupWindow = ListPopupWindow(context).apply {
    adapter.setItems(items)
    setAdapter(adapter)

    anchorView = anchor
    isModal = true

    setOnItemClickListener { _, _, position, _ ->
        onSelection(items[position])
        dismiss()
    }
    show()
}

