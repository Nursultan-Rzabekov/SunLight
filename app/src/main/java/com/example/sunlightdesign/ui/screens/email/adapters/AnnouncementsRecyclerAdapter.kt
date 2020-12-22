package com.example.sunlightdesign.ui.screens.email.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.email.entity.Data
import com.example.sunlightdesign.utils.DateUtils
import com.example.sunlightdesign.utils.EmptyViewHolder
import kotlinx.android.synthetic.main.announcement_item.view.*
import java.util.*

class AnnouncementsRecyclerAdapter(
    private val context: Context,
    private val announcements: AnnouncementSelector,
    private var items: List<Data>,
    showEmptyViewHolder: Boolean = true
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val EMPTY_LIST = -1
        private const val NOT_EMPTY_LIST = 0

        val EMPTY = Data(
            created_at = null,
            id = null,
            message_body = null,
            message_title = null,
            updated_at = null,
            user_id = null,
            read = null
        )
    }

    init {
        if (showEmptyViewHolder && items.isEmpty()) {
            setEmptyItem()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            EMPTY_LIST -> {
                val view =
                    LayoutInflater.from(context).inflate(EmptyViewHolder.getLayoutId(), parent, false)
                EmptyViewHolder(view)
            }
            else -> {
                val view =
                    LayoutInflater.from(context).inflate(R.layout.announcement_item, parent, false)
                AnnouncementViewHolder(view, announcements)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is EmptyViewHolder -> {
                holder.bind(context.getString(R.string.you_dont_have_messages))
            }
            is AnnouncementViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (items.size == 1 && items.first().id == null) {
            EMPTY_LIST
        } else {
            NOT_EMPTY_LIST
        }

    private fun setEmptyItem() {
        items = listOf(EMPTY)
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

            item.read?.let {
                itemView.itemViewConstraintLayout.background = ContextCompat.getDrawable(
                    itemView.context,
                    if (it == 1) {
                        R.drawable.announcement_item_shape_read
                    } else {
                        R.drawable.announcement_item_shape_unread
                    }
                )
            }

            itemView.setOnClickListener {
                item.id?.let { it1 -> announcementSelector.onAnnouncementSelected(it1) }
            }
        }
    }

    interface AnnouncementSelector {
        fun onAnnouncementSelected(id: Int)
    }
}