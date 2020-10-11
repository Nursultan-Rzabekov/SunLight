package com.example.sunlightdesign.ui.screens.email.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.email.entity.Data
import kotlinx.android.synthetic.main.announcement_item.view.*

class AnnouncementsRecyclerAdapter(
    private val context: Context,
    private val announcements: AnnouncementSelector,
    private val items : List<Data>
) : RecyclerView.Adapter<AnnouncementsRecyclerAdapter.AnnouncementViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnouncementViewHolder {
        val view =  LayoutInflater.from(context).inflate(R.layout.announcement_item,parent,false)
        return AnnouncementViewHolder(view, announcements)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: AnnouncementViewHolder, position: Int) {
        holder.bind(item = items[position])
    }

    class AnnouncementViewHolder constructor(
        itemView: View,
        private val announcementSelector: AnnouncementSelector
    ): RecyclerView.ViewHolder(itemView){
        fun bind(item: Data) = with(itemView){

            itemView.dataTextView.text = item.created_at
            itemView.messageTitleTextView.text = item.message_title
            itemView.messageBodyTextView.text = item.message_body

            itemView.setOnClickListener {
                item.id?.let { it1 -> announcementSelector.onAnnouncementSelected(it1) }
                itemView.itemViewConstraintLayout.background = ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.announcement_item_shape_read,
                null)
            }
        }
    }

    interface AnnouncementSelector{
        fun onAnnouncementSelected(id : Int)
    }
}