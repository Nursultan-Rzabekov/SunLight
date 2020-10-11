package com.example.sunlightdesign.data.source.dataSource.remote.email

import com.example.sunlightdesign.data.source.dataSource.remote.email.entity.Announcements
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface AnnouncementsServices {

    @GET("/announcements")
    fun getAnnouncements(): Deferred<Announcements>

    @GET("/announcements/show/")
    fun showAnnouncementsDetail(
        @Query("id") id: Int
    ): Deferred<Announcements>

    @PUT("/announcements/delete/")
    fun deleteAnnouncement(
        @Query("id") id: Int
    ): Deferred<Announcements>

}