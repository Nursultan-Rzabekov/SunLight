package com.corp.sunlightdesign.data.source.dataSource.remote.email

import com.corp.sunlightdesign.data.source.dataSource.remote.email.entity.AnnouncementItem
import com.corp.sunlightdesign.data.source.dataSource.remote.email.entity.Announcements
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface AnnouncementsServices {

    @GET("announcements")
    fun getAnnouncements(): Deferred<Announcements>

    @GET("announcements/show/{id}")
    fun showAnnouncementsDetail(
        @Path("id") id: Int
    ): Deferred<AnnouncementItem>

    @PUT("announcements/delete/{id}")
    fun deleteAnnouncement(
        @Path("id") id: Int
    ): Deferred<Announcements>

}