package com.corp.sunlightdesign.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.corp.sunlightdesign.BuildConfig
import com.corp.sunlightdesign.R
import com.corp.sunlightdesign.usecase.usercase.authUse.SetFirebaseTokenUseCase
import com.corp.sunlightdesign.utils.SecureSharedPreferences
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.koin.core.KoinComponent
import org.koin.core.inject
import timber.log.Timber

class SunlightFirebaseMessagingService : FirebaseMessagingService(), KoinComponent {

    private val setFirebaseTokenUseCase: SetFirebaseTokenUseCase by inject()

    private val sharedPreferences: SecureSharedPreferences by inject()

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.d(token)
        if (sharedPreferences.bearerToken.isNullOrBlank()) return
        setFirebaseTokenUseCase.setModel(token)
        setFirebaseTokenUseCase.execute {
            onComplete {
                Timber.d("firebase token is sent")
            }
            onError {
                Timber.d("firebase token is not sent: $it")
                if (!BuildConfig.DEBUG) return@onError
                AlertDialog.Builder(this@SunlightFirebaseMessagingService.applicationContext)
                    .setTitle("Firebase")
                    .setMessage(it.toString())
                    .show()
            }
            onNetworkError {
                Timber.d("firebase token is not sent: $it")
                if (!BuildConfig.DEBUG) return@onNetworkError
                AlertDialog.Builder(this@SunlightFirebaseMessagingService.applicationContext)
                    .setTitle("Firebase")
                    .setMessage(it.toString())
                    .show()
            }
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Timber.d(remoteMessage.notification?.body)
        createChannel()
        val largeIcon = BitmapFactory.decodeResource(resources, R.mipmap.notification_icon)
        val notification = NotificationCompat.Builder(
            this,
            getString(R.string.default_notification_channel_id)
        ).setContentTitle(remoteMessage.notification?.title)
            .setContentText(remoteMessage.notification?.body)
            .setSmallIcon(R.mipmap.notification_icon)
            .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
            .setAutoCancel(true)
            .setChannelId(getString(R.string.default_notification_channel_id))
            .build()

        val manager = NotificationManagerCompat.from(applicationContext)
        manager.notify(0, notification)
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val channelId = getString(R.string.default_notification_channel_id)
        val channel = NotificationChannel(
            channelId,
            "Sunlight",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.enableLights(true)
        channel.enableVibration(true)
        val manager = NotificationManagerCompat.from(applicationContext)
        manager.createNotificationChannel(channel)
    }
}