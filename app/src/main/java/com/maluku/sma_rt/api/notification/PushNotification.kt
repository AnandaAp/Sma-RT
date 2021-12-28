package com.maluku.sma_rt.api.notification

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_DEFAULT
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC
import androidx.core.app.NotificationManagerCompat
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.content.Context
import android.os.Build
import com.maluku.sma_rt.R

const val channelId = "313"
const val channelName = "Push Message Notification"
class PushNotification: FirebaseMessagingService() {
    private val TAG = "FIREBASE MESSAGE"

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if(message.notification != null){
            Log.d(TAG, "ada notifikasi" +
                    "\npesan: ${message.notification!!.body.toString()}")
        }
        val title = message.notification!!.title.toString()
        val body = message.notification!!.body.toString()
        showNotification(title, body)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun showNotification(title: String, message: String){
        val builder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(PRIORITY_DEFAULT)
            .setSmallIcon(R.mipmap.logosmart_foreground)
            .setAutoCancel(true)
            .setSilent(false)
            .setVisibility(VISIBILITY_PUBLIC)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationCannel = NotificationChannel(channelId,channelName,IMPORTANCE_DEFAULT)
            notificationCannel.description = message
            notificationManager.createNotificationChannel(notificationCannel)
        }
//        notificationManager.notify(314, builder.build())
        with(NotificationManagerCompat.from(this)) {
//             notificationId is a unique int for each notification that you must define
            notify(314, builder.build())
            notificationManager.notify(314, builder.build())
        }
    }
}