package com.example.sunlightdesign.ui.screens.email.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.email.entity.Data
import com.example.sunlightdesign.utils.DateUtils
import kotlinx.android.synthetic.main.announcement_item.view.*
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class AnnouncementsRecyclerAdapter(
    private val context: Context,
    private val announcements: AnnouncementSelector,
    private val items: List<Data>
) : RecyclerView.Adapter<AnnouncementsRecyclerAdapter.AnnouncementViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnouncementViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.announcement_item, parent, false)
        return AnnouncementViewHolder(view, announcements)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: AnnouncementViewHolder, position: Int) {
        holder.bind(item = items[position])
    }

    class AnnouncementViewHolder constructor(
        itemView: View,
        private val announcementSelector: AnnouncementSelector
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Data) = with(itemView) {
            val date = item.created_at?.let { DateUtils.convertLongStringToDate(it) } ?: Date()

            itemView.dataTextView.text = DateUtils.convertDateToString(date)
            itemView.messageTitleTextView.text = item.message_title
            itemView.messageBodyTextView.text = item.message_body

            itemView.setOnClickListener {
                item.id?.let { it1 -> announcementSelector.onAnnouncementSelected(it1) }
            }
        }
    }

    interface AnnouncementSelector {
        fun onAnnouncementSelected(id: Int)
    }
}