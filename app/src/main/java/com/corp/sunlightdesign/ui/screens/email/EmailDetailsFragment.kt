package com.corp.sunlightdesign.ui.screens.email

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.data.source.dataSource.remote.email.entity.AnnouncementItem
import com.corp.sunlightdesign.data.source.dataSource.remote.email.entity.File
import com.corp.sunlightdesign.ui.base.StrongFragment
import com.corp.sunlightdesign.ui.screens.email.adapters.AttachedFilesAdapter
import com.corp.sunlightdesign.utils.DateUtils
import com.corp.sunlightdesign.utils.checkFilePermission
import com.corp.sunlightdesign.utils.getFileUrlPath
import com.corp.sunlightdesign.utils.showToast
import kotlinx.android.synthetic.main.announcement_detail_item.*
import java.lang.Exception
import java.util.*

class EmailDetailsFragment : StrongFragment<EmailViewModel>(EmailViewModel::class), AttachedFilesAdapter.AttachFileInteraction {

    private lateinit var filesAdapter: AttachedFilesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.announcement_detail_item, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        configViewModel()

        viewModel.showAnnouncementDetail(viewModel.itemId.id)
    }

    private fun configViewModel() {
        viewModel.apply {
            progress.observe(viewLifecycleOwner, Observer {
                progress_bar.isVisible = it
            })

            announcementItem.observe(viewLifecycleOwner, Observer {
                fillFields(it)
                initRecyclers(it.announcement.files)
            })
        }
    }

    private fun initRecyclers(files: List<File>) {
        filesRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            filesAdapter = AttachedFilesAdapter(files, this@EmailDetailsFragment)
            adapter = filesAdapter
        }
    }

    private fun fillFields(item: AnnouncementItem) {
        val author = "${item.announcement.author.first_name} ${item.announcement.author.last_name}"
        fromNameTextView.text = getString(R.string.from_mail, author)
        itemTitleTextView.text = item.announcement.message_title
        itemBodyTextView.text = item.announcement.message_body
        dateTextView.text = DateUtils.reformatDateString(
            item.announcement.created_at,
            DateUtils.PATTERN_DD_MM_YYYY_HH_mm
        )
    }

    override fun onFileClicked(file: File) {
        if (!checkFilePermission(requireActivity())) return

        val request = DownloadManager.Request(Uri.parse(getFileUrlPath(file.attached_file_path)))
        request.setTitle(file.attached_file_name)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, file.attached_file_name)

        val downloadManager = requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        try {
            Objects.requireNonNull(downloadManager).enqueue(request)
        } catch (e: Exception) {
            showToast(e.message.toString())
        }
    }
}