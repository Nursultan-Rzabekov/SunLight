package com.example.sunlightdesign.data.source.dataSource.remote.email

import com.example.sunlightdesign.data.source.dataSource.remote.email.entity.AnnouncementItem
import com.example.sunlightdesign.data.source.dataSource.remote.email.entity.Announcements
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

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