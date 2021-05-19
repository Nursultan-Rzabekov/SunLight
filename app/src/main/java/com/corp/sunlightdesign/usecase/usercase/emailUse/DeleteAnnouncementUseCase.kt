package com.corp.sunlightdesign.usecase.usercase.emailUse


import com.corp.sunlightdesign.data.source.MessengerRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.email.entity.Announcements
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase


class DeleteAnnouncementUseCase constructor(
    private val itemsRepository: MessengerRepository
) : BaseCoroutinesUseCase<Announcements?>() {

    private var id: Int? = null

    fun setData(id: Int) {
        this.id = id
    }

    override suspend fun executeOnBackground(): Announcements? =
        this.id?.let { itemsRepository.deleteAnnouncement(it) }
}

