package com.example.sunlightdesign.usecase.usercase.emailUse.get


import com.example.sunlightdesign.data.source.AuthRepository
import com.example.sunlightdesign.data.source.MessengerRepository
import com.example.sunlightdesign.data.source.dataSource.remote.auth.entity.Login
import com.example.sunlightdesign.data.source.dataSource.remote.email.entity.Announcements
import com.example.sunlightdesign.usecase.BaseCoroutinesUseCase



class GetAnnouncementsUseCase  constructor(
    private val itemsRepository: MessengerRepository
) : BaseCoroutinesUseCase<Announcements?>() {

    override suspend fun executeOnBackground(): Announcements? = itemsRepository.getAnnouncements()
}

