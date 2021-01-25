package com.example.sunlightdesign.ui.screens.profile.verification

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sunlightdesign.R
import com.example.sunlightdesign.utils.getFileName
import com.example.sunlightdesign.utils.getFileSizeInLong
import com.example.sunlightdesign.utils.getImageUrl
import kotlinx.android.synthetic.main.document_layout_item.view.*

class DocumentAttachmentAdapter(
    private val documents: MutableList<DocumentAttachmentEntity>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val REMOTE = 1
        const val LOCAL = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            REMOTE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.document_layout_item, parent, false)
                RemoteDocumentViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.document_layout_item, parent, false)
                LocalDocumentViewHolder(view)
            }
        }

    override fun getItemCount(): Int = documents.size

    override fun getItemViewType(position: Int): Int {
        val doc = documents[position]
        return when {
            doc.uri != null -> LOCAL
            else -> REMOTE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RemoteDocumentViewHolder -> holder.bind(documents[position].url)
            is LocalDocumentViewHolder -> holder.bind(documents[position].uri)
        }
    }

    private fun removeItem(position: Int) {
        documents.removeAt(position)
        notifyDataSetChanged()
    }

    fun addItem(document: DocumentAttachmentEntity) {
        documents.add(document)
        notifyDataSetChanged()
    }

    fun getLocalFiles(): List<Uri> {
        val uris = mutableListOf<Uri>()
        documents.forEach { if (it.uri != null) uris.add(it.uri) }
        return uris
    }

    inner class LocalDocumentViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(uri: Uri?) {
            uri ?: return
            itemView.documentNameTextView.text = getFileName(itemView.context, uri)
            itemView.documentSizeTextView.text = getFileSizeInLong(itemView.context, uri).toString()
            itemView.documentImageView.setImageURI(uri)
            itemView.documentRemoveTextView.setOnClickListener {
                removeItem(adapterPosition)
            }
        }
    }

    inner class RemoteDocumentViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(url: String?) {
            url ?: return
            Glide.with(itemView)
                .load(getImageUrl(url))
                .into(itemView.documentImageView)
            itemView.documentRemoveTextView.setOnClickListener {
               removeItem(adapterPosition)
            }
        }
    }
}

data class DocumentAttachmentEntity(
    val url: String?,
    val uri: Uri?
)
