package com.corp.sunlightdesign.data.source

import com.corp.sunlightdesign.data.source.dataSource.remote.email.entity.AnnouncementItem
import com.corp.sunlightdesign.data.source.dataSource.remote.email.entity.Announcements

/**
 * Interface to the data layer.
 */
interface MessengerRepository {
    suspend fun getAnnouncements(): Announcements
    suspend fun showAnnouncementsDetail(id: Int): AnnouncementItem
    suspend fun deleteAnnouncement(id: Int): Announcements
}
