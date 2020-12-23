package com.example.sunlightdesign.ui.screens.home.structure.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.User
import com.example.sunlightdesign.utils.getImageUrl
import kotlinx.android.synthetic.main.user_status_item.view.*

class UserStatusesAdapter(
    private val userStatuses: List<User>
): RecyclerView.Adapter<UserStatusesAdapter.UserStatusViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserStatusViewHolder {
        return UserStatusViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.user_status_item, parent, false)
        )
    }

    override fun getItemCount(): Int = userStatuses.size

    override fun onBindViewHolder(holder: UserStatusViewHolder, position: Int) {
        holder.bind(userStatuses[position])
    }

    class UserStatusViewHolder(
        view: View
    ): RecyclerView.ViewHolder(view) {
        fun bind(user: User) {
            itemView.userFullNameTextView.text = ("${user.first_name} ${user.last_name}")
            itemView.userStatusTextView.text = user.status?.status_name

            Glide.with(itemView)
                .load(getImageUrl(user.user_avatar_path))
                .into(itemView.userCircleImageView)
        }
    }
}