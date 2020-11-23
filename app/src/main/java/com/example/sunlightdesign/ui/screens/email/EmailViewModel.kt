package com.example.sunlightdesign.ui.screens.email

import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sunlightdesign.R
import com.example.sunlightdesign.data.source.dataSource.ItemId
import com.example.sunlightdesign.data.source.dataSource.remote.email.entity.AnnouncementItem
import com.example.sunlightdesign.data.source.dataSource.remote.email.entity.Announcements
import com.example.sunlightdesign.ui.base.StrongViewModel
import com.example.sunlightdesign.usecase.usercase.emailUse.DeleteAnnouncementUseCase
import com.example.sunlightdesign.usecase.usercase.emailUse.get.GetAnnouncementsUseCase
import com.example.sunlightdesign.usecase.usercase.emailUse.get.ShowAnnouncementsDetailsUseCase

/**
 * ViewModel for the task list screen.
 */
class EmailViewModel constructor(
    private val getAnnouncementsUseCase: GetAnnouncementsUseCase,
    private val showAnnouncementsDetailsUseCase: ShowAnnouncementsDetailsUseCase,
    private val deleteAnnouncementUseCase: DeleteAnnouncementUseCase
) : StrongViewModel() {

    var progress = MutableLiveData<Boolean>(false)

    var itemId: ItemId.Builder = ItemId.Builder()

    private val _announcementList = MutableLiveData<Announcements>()
    val announcementList: LiveData<Announcements> get() = _announcementList

    private val _announcementItem = MutableLiveData<AnnouncementItem>()
    val announcementItem: LiveData<AnnouncementItem> get() = _announcementItem

    fun getAnnouncementsList() {
        progress.postValue(true)
        getAnnouncementsUseCase.execute {
            onComplete {
                progress.postValue(false)
                _announcementList.postValue(it)
            }
            onNetworkError {
                progress.postValue(false)
            }
            onError {
                progress.postValue(false)
            }
        }
    }

    fun showAnnouncementDetail(id: Int) {
        progress.postValue(true)
        showAnnouncementsDetailsUseCase.setData(id)
        showAnnouncementsDetailsUseCase.execute {
            onComplete { announcementItem ->
                progress.postValue(false)
                _announcementItem.postValue(announcementItem)
//                withActivity {
//                    (it.findViewById(R.id.itemBodyTextView) as TextView).text =
//                        announcementItem?.announcement?.message_body
//                    (it.findViewById(R.id.itemTitleTextView) as TextView).text =
//                        announcementItem?.announcement?.message_title
//                    (it.findViewById(R.id.dateTextView) as TextView).text =
//                        announcementItem?.announcement?.created_at
//                    (it.findViewById(R.id.toNameTextView) as TextView).text =
//                        announcementItem?.announcement?.author?.first_name
//                }
            }
            onNetworkError {
                progress.postValue(false)
            }
            onError {
                progress.postValue(false)
            }
        }
    }

    fun deleteAnnouncement(id: Int) {
        progress.postValue(true)
        deleteAnnouncementUseCase.setData(id)
        deleteAnnouncementUseCase.execute {
            onComplete {
                progress.postValue(false)
            }
            onNetworkError {
                progress.postValue(false)
            }
            onError {
                progress.postValue(false)
            }
        }
    }

}


