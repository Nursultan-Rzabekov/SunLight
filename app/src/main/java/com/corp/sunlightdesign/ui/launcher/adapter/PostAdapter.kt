package com.corp.sunlightdesign.ui.launcher.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.corp.sunlightdesign.BuildConfig
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.data.source.dataSource.remote.main.entity.Post
import com.corp.sunlightdesign.utils.DateUtils
import kotlinx.android.synthetic.main.main_page_post_item.view.*

class PostAdapter(
    private val context: Context,
    private val posts: List<Post>,
    private val postInteraction: PostInteraction
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.main_page_post_item, parent, false)
        return PostViewHolder(view, postInteraction)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount() = posts.size

    class PostViewHolder(
        itemView: View,
        private val postInteraction: PostInteraction
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(post: Post) {

            println(BuildConfig.BASE_URL_IMAGE + post.image)

            Glide.with(itemView)
                .load(BuildConfig.BASE_URL_IMAGE + post.image)
                .centerCrop()
                .into(itemView.post_image_view)

            itemView.title_text_view.text = post.title
            itemView.description_text_view.text = post.description

            val date = DateUtils.convertLongStringToDate(post.created_at, DateUtils.PATTERN_FULL_DATE)
            itemView.time_text_view.text = DateUtils.convertDateToString(date)

            itemView.postLayout.setOnClickListener {
                postInteraction.onPostClicked(post.id)
            }
        }
    }

    interface PostInteraction {
        fun onPostClicked(id: Int)
    }
}