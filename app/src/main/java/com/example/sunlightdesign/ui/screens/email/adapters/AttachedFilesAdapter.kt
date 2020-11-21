package com.example.sunlightdesign.ui.screens.email.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.remote.email.entity.File
import kotlinx.android.synthetic.main.attached_file_recycler_item.view.*

class AttachedFilesAdapter(
    private val filesList: List<File>,
    private val attachFileInteraction: AttachFileInteraction
): RecyclerView.Adapter<AttachedFilesAdapter.AttachFileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttachFileViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.attached_file_recycler_item, parent, false)
        return AttachFileViewHolder(view, attachFileInteraction)
    }

    override fun getItemCount(): Int = filesList.size

    override fun onBindViewHolder(holder: AttachFileViewHolder, position: Int) {
        holder.bind(filesList[position])
    }

    class AttachFileViewHolder(
        view: View,
        private val interaction: AttachFileInteraction
    ) : RecyclerView.ViewHolder(view) {
        fun bind(file: File) = with(itemView) {
            this.fileNameTextView.text = file.attached_file_name
            this.fileSizeTextView.text = ("(${file.attached_file_size})")

            this.attachFileLayout.setOnClickListener {
                interaction.onFileClicked(file)
            }
        }
    }

    interface AttachFileInteraction{
        fun onFileClicked(file: File)
    }
}