package com.corp.sunlightdesign.data.source.dataSource.remote.orders.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class MyTickets(
    val events: List<Ticket>
)

@Parcelize
data class Ticket(
    val id: Int?,
    val name: String?,
    @SerializedName("event_id")
    val eventId: Int?,
    val adult: Int?,
    val child: Int?,
    val price: Int?,
    @SerializedName("commentary_user")
    val commentaryUser: String?,
    @SerializedName("payment_type")
    val paymentType: String?,
    val paid: Int?,
    val entered: Int?,
    val qr: String?,
    @SerializedName("commentary_worker")
    val commentaryWorker: String?,
    @SerializedName("office_id")
    val officeId: Int?,
    val event: Event
) : Parcelable