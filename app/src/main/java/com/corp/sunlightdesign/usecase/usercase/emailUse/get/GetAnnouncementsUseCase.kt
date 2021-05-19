package com.corp.sunlightdesign.usecase.usercase.emailUse.get


import com.corp.sunlightdesign.data.source.MessengerRepository
import com.corp.sunlightdesign.data.source.dataSource.remote.email.entity.Announcements
import com.corp.sunlightdesign.usecase.BaseCoroutinesUseCase


class GetAnnouncementsUseCase constructor(
    private val itemsRepository: MessengerRepository
) : BaseCoroutinesUseCase<Announcements?>() {

    override suspend fun executeOnBackground(): Announcements? = itemsRepository.getAnnouncements()
}

