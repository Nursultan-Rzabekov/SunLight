

package com.example.sunlightdesign.data.source

import com.example.sunlightdesign.data.source.dataSource.remote.email.entity.Announcements

/**
 * Interface to the data layer.
 */
interface MessengerRepository {
    suspend fun getAnnouncements() : Announcements
    suspend fun showAnnouncementsDetail(id: Int) : Announcements
    suspend fun deleteAnnouncement(id: Int): Announcements
}
