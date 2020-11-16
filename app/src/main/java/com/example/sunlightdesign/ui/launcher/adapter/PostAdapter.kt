package com.example.sunlightdesign.ui.launcher.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sunlightdesign.BuildConfig
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.main.entity.Post
import com.example.sunlightdesign.utils.DateUtils
import kotlinx.android.synthetic.main.main_page_post_item.view.*

class PostAdapter() : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private var posts: List<Post> = listOf()

    fun setItems(posts: List<Post>) {
        this.posts = posts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.main_page_post_item, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {

    }

    override fun getItemCount() = posts.size

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(post: Post) {

            Glide.with(itemView)
                .load(BuildConfig.BASE_URL + post.image)
                .placeholder(R.drawable.main_photo)
                .error(R.drawable.main_photo)
                .centerCrop().into(itemView.post_image_view)

            itemView.title_text_view.text = post.title
            itemView.description_text_view.text = post.description

            val date = DateUtils.convertLongStringToDate(post.created_at)
            itemView.time_text_view.text = DateUtils.convertDateToString(date)
        }
    }
}