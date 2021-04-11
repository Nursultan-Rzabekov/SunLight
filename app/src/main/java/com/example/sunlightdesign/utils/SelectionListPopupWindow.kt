package com.example.sunlightdesign.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.appcompat.widget.ListPopupWindow
import com.example.sunlightdesign.R
import kotlinx.android.synthetic.main.item_popup.view.*

open class BasePopUpAdapter<T>: BaseAdapter() {

    protected var allItems = mutableListOf<T>()

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_popup,
            parent,
            false
        )
        view.value.text = allItems[position].toString()
        return view
    }

    override fun getItem(position: Int): Any = allItems[position].toString()

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = allItems.size

    fun setItems(items: List<T>) {
        this.allItems.clear()
        this.allItems.addAll(items)
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

