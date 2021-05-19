package com.corp.sunlightdesign.utils.views

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.utils.BasePopUpAdapter
import kotlinx.android.synthetic.main.country_selection_item.view.*

class CountryCodeBaseAdapter: BasePopUpAdapter<CountryCode>() {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.country_selection_item,
            parent,
            false
        )
        view.valueTextView.text = allItems[position].toString()
        view.flagImageView.setImageDrawable(ContextCompat.getDrawable(
            view.context,
            getCountryFlag(allItems[position].code)
        ))
        return view
    }
}