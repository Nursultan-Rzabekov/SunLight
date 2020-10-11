package com.example.sunlightdesign.ui.screens.email

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sunlightdesign.data.source.AuthRepository
import com.example.sunlightdesign.data.source.dataSource.remote.email.entity.Announcements
import com.example.sunlightdesign.ui.base.StrongViewModel
import com.example.sunlightdesign.usecase.usercase.emailUse.DeleteAnnouncementUseCase
import com.example.sunlightdesign.usecase.usercase.emailUse.get.GetAnnouncementsUseCase
import com.example.sunlightdesign.usecase.usercase.emailUse.get.ShowAnnouncementsDetailsUseCase

/**
 * ViewModel for the task list screen.
 */
class EmailViewModel  constructor(
    private val getAnnouncementsUseCase: GetAnnouncementsUseCase,
    private val showAnnouncementsDetailsUseCase: ShowAnnouncementsDetailsUseCase,
    private val deleteAnnouncementUseCase: DeleteAnnouncementUseCase
) : StrongViewModel() {

    var progress = MutableLiveData<Boolean>(false)

    private val _announcementList = MutableLiveData<Announcements>()
    val announcementList: LiveData<Announcements> get() = _announcementList

    fun getAnnouncementsList(){
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

    fun showAnnouncementDetail(id: Int){
        progress.postValue(true)
        showAnnouncementsDetailsUseCase.setData(id)
        showAnnouncementsDetailsUseCase.execute {
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

    fun deleteAnnouncement(id:Int){
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


