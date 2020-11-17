package com.example.sunlightdesign.ui.screens.profile.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.Child
import com.example.sunlightdesign.utils.DateUtils
import kotlinx.android.synthetic.main.invited_list_item.view.*

class InvitedAdapter(
    private var items: ArrayList<Child> = arrayListOf()
) : RecyclerView.Adapter<InvitedAdapter.InvitedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvitedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.invited_list_item, parent, false)
        return InvitedViewHolder(view)
    }

    override fun onBindViewHolder(holder: InvitedViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<Child>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class InvitedViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        fun bind(item: Child) {
            itemView.invitedFullNameTextView.text = ("${item.first_name} ${item.last_name}")
            itemView.invitedUuidTextView.text = item.uuid
            itemView.invitedActivityTextView.text =
                if (item.is_active == 1.0)
                    itemView.context.getString(R.string.active)
                else
                    itemView.context.getString(R.string.not_active)
            item.created_at?.let {
                itemView.invitedDateTextView.text =
                    DateUtils.reformatDateString(it, pattern = DateUtils.PATTERN_DD_MM_YYYY)
            }
        }
    }
}