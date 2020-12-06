package com.example.sunlightdesign.ui.screens.profile.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.email.entity.Data
import com.example.sunlightdesign.data.source.dataSource.remote.profile.entity.Child
import com.example.sunlightdesign.utils.DateUtils
import com.example.sunlightdesign.utils.EmptyViewHolder
import kotlinx.android.synthetic.main.invited_list_item.view.*

class InvitedAdapter(
    private var items: ArrayList<Child> = arrayListOf(),
    private val showEmptyViewHolder: Boolean = true
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val EMPTY_LIST = -1
        private const val NOT_EMPTY_LIST = 0

        val EMPTY = Child(
            block_status = null,
            childs = null,
            city_id = null,
            country_id = null,
            created_at = null,
            direct_id = null,
            direct_level = null,
            document_back_path = null,
            document_front_path = null,
            first_name = null,
            id = null,
            iin = null,
            is_active = null,
            last_name = null,
            left_total = null,
            level = null,
            middle_name = null,
            office_id = null,
            package_id = null,
            parent_id = null,
            parent_level = null,
            phone = null,
            phone_verified_at = null,
            position = null,
            referral_link = null,
            region_id = null,
            register_by = null,
            right_total = null,
            status_id = null,
            step = null,
            system_status = null,
            uuid = null,
            wallet_main_wallet = null
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            EMPTY_LIST -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(EmptyViewHolder.getLayoutId(), parent, false)
                EmptyViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.invited_list_item, parent, false)
                InvitedViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is InvitedViewHolder -> {
                holder.bind(items[position])
            }
            is EmptyViewHolder -> {
                holder.bind(holder.itemView.context.getString(R.string.you_dont_have_invited_people))
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int =
        if (items.size == 1 && items.first() == EMPTY) {
            EMPTY_LIST
        } else {
            NOT_EMPTY_LIST
        }

    fun setItems(items: List<Child>) {
        this.items.clear()
        this.items.addAll(items)
        if (items.isEmpty() && showEmptyViewHolder) {
            this.items.add(EMPTY)
        }
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