package com.corp.sunlightdesign.usecase.usercase.emailUse.get


import com.corp.sunlightdesign.data.source.MessengerRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.email.entity.AnnouncementItem
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase


class ShowAnnouncementsDetailsUseCase constructor(
    private val itemsRepository: MessengerRepository
) : BaseCoroutinesUseCase<AnnouncementItem?>() {

    private var id: Int? = null

    fun setData(id: Int) {
        this.id = id
    }

    override suspend fun executeOnBackground(): AnnouncementItem? =
        this.id?.let { itemsRepository.showAnnouncementsDetail(it) }
}

