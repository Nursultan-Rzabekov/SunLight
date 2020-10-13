package com.example.sunlightdesign.data.source.repositories

import com.example.sunlightdesign.data.source.MessengerRepository
import com.example.sunlightdesign.data.source.dataSource.remote.email.AnnouncementsServices
import com.example.sunlightdesign.data.source.dataSource.remote.email.entity.AnnouncementItem
import com.example.sunlightdesign.data.source.dataSource.remote.email.entity.Announcements


class DefaultMessengerRepository constructor(
    private val announcementsServices: AnnouncementsServices
) : MessengerRepository {

    override suspend fun getAnnouncements(): Announcements =
        announcementsServices.getAnnouncements().await()

    override suspend fun showAnnouncementsDetail(id: Int): AnnouncementItem =
        announcementsServices.showAnnouncementsDetail(id).await()

    override suspend fun deleteAnnouncement(id: Int): Announcements =
        announcementsServices.deleteAnnouncement(id).await()


}
