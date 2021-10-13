package com.base.notifications

import android.app.*
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.zaka.BuildConfig
import com.zaka.R

const val ONGOING_NOTIFICATION_ID = 1
const val ALERT_PERMISSION_NOTIFICATION_ID = 2
const val ALERT_GPS_NOTIFICATION_ID = 3
const val NOTIFICATION_CHANNEL_NAME = "All"
const val NOTIFICATION_CHANNEL_ONGOING_ID = "${BuildConfig.APPLICATION_ID}.ongoing"
const val NOTIFICATION_CHANNEL_ALERTS_ID = "${BuildConfig.APPLICATION_ID}.alerts"

class NotificationsUtil(private val context: Context) {

    init {
        cancelAlertNotification() //to clear if there were any notifications
    }

    private val vibrationFlow = longArrayOf(0, 400, 200, 400)
    private val channelId ="BeryPos-2020"
    private val channelName = "BeryPos"
    private val channelDescription = "BeryPos app channel"

    fun createOngoingNotification(
        service: Service,
        title: String,
        text: String,
        pendingIntent: PendingIntent? = null
    ) {
        createOngoingNotificationChannel()
        service.startForeground(
            ONGOING_NOTIFICATION_ID,
            NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ONGOING_ID)
                .setContentTitle(title)
                .setOngoing(true)
                .setContentText(text)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .build()
        )
    }


    fun createForegroundNotification(title: String,
                                     text: String,
                                     pendingIntent: PendingIntent? = null): Notification? {
        createOngoingNotificationChannel()
        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ONGOING_ID)
            .setContentTitle(title)
            .setOngoing(true)
            .setContentText(text)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .build()
    }

    fun createPendingIntent(componentName: ComponentName, context: Context): PendingIntent {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.component = componentName
        return  PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }



    fun createAlertNotification(
        id: Int,
        title: String,
        text: String,
        subText:String?="",
        pendingIntent: PendingIntent? = null
    ) {

        createNotificationChannelIfAvailable(context)
        val notificationManager = context. getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ALERTS_ID)
            .setContentTitle(title)
            .setAutoCancel(true)
            .setContentText(text)
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .setSubText(subText)
            .setOnlyAlertOnce(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                NOTIFICATION_CHANNEL_ALERTS_ID,
                NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
                .apply { vibrationPattern = vibrationFlow }
                .let { notificationManager.createNotificationChannel(it) }
        } else {
            notificationBuilder
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(vibrationFlow)
        }

        notificationManager.notify(id, notificationBuilder.build())
    }

    private fun createNotificationChannelIfAvailable(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = channelName
            val descriptionText = channelDescription
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager =
                context.  getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createOngoingNotificationChannel() {
        val notificationManager = context. getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                NOTIFICATION_CHANNEL_ONGOING_ID, NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_MIN
            )
                .let { channel ->
                    notificationManager.createNotificationChannel(channel)
                }
        }
    }

    fun cancelAlertNotification() {
        val notificationManager = context. getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(ALERT_GPS_NOTIFICATION_ID)
        notificationManager.cancel(ALERT_PERMISSION_NOTIFICATION_ID)
    }
}