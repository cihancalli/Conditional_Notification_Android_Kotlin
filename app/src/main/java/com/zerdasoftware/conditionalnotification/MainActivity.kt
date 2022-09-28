package com.zerdasoftware.conditionalnotification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var count = 1
    @SuppressLint("UnspecifiedImmutableFlag", "ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_notification.setOnClickListener {

            val builder : NotificationCompat.Builder

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val intent = Intent(this,MainActivity::class.java)

            //PendingIntent.FLAG_UPDATE_CURRENT

            val goToIntent = PendingIntent.getActivity(applicationContext,1,intent,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

            if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {

                val channelID = "channelID$count"
                val channelName = "channelName$count"
                val channelDescription = "channelDescription$count"
                val channelPriority = NotificationManager.IMPORTANCE_HIGH

                var channel: NotificationChannel? = notificationManager.getNotificationChannel(channelID)

                if (channel == null) {
                    channel = NotificationChannel(channelID,channelName,channelPriority)
                    channel.description = channelDescription
                    notificationManager.createNotificationChannel(channel)
                }

                builder = NotificationCompat.Builder(this,channelID)
                builder.setContentTitle("Başlık$count")
                    .setContentText("İçerik$count")
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentIntent(goToIntent)
                    .setAutoCancel(true)

            }

            else {
                builder = NotificationCompat.Builder(this)
                builder.setContentTitle("Başlık$count")
                    .setContentText("İçerik$count")
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentIntent(goToIntent)
                    .setAutoCancel(true)
                    .priority = Notification.PRIORITY_HIGH
            }

            notificationManager.notify(count,builder.build())
            count ++
        }
    }
}