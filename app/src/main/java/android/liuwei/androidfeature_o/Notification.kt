@file:Suppress("ClassName")

package android.liuwei.androidfeature_o

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

abstract class NotificationAlert {
    abstract fun showNotification(context: Context,
                                  channelId: String,
                                  channelName: String,
                                  showBadge: Boolean,
                                  showLight: Boolean)

    companion object {
        fun getInstance(): NotificationAlert {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) NotificationAlertImpl_V26()
            else NotificationAlertImpl_V21()
        }
    }

    protected fun getBuilder(context: Context, channelId: String, channelName: String): Notification.Builder {
        return Notification
                .Builder(context)
                .setContentTitle("channelId: $channelId")
                .setContentText("channelName: $channelName")
                .setSmallIcon(R.drawable.notification_alert_ic)
    }
}

@TargetApi(value = Build.VERSION_CODES.O)
class NotificationAlertImpl_V26 : NotificationAlert() {
    override fun showNotification(context: Context,
                                  channelId: String,
                                  channelName: String,
                                  showBadge: Boolean,
                                  showLight: Boolean) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel: NotificationChannel? = manager.getNotificationChannel(channelId)
        if (channel == null) {
            val newChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            newChannel.description = channelName
            newChannel.setShowBadge(showBadge)
            newChannel.enableLights(showLight)
            manager.createNotificationChannel(newChannel)
        } else {
            channel.setShowBadge(showBadge)
            channel.enableLights(showLight)
        }
        manager.notify(777,
                getBuilder(context, channelId, channelName)
                        .build()
        )
    }
}

@TargetApi(value = Build.VERSION_CODES.LOLLIPOP)
class NotificationAlertImpl_V21 : NotificationAlert() {
    override fun showNotification(context: Context,
                                  channelId: String,
                                  channelName: String,
                                  showBadge: Boolean,
                                  showLight: Boolean) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(777,
                getBuilder(context, channelId, channelName)
                        .build())
    }
}