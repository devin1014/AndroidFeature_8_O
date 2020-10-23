package android.liuwei.androidfeature_o

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val channelId by lazy { findViewById<EditText>(R.id.notification_channel_id_value) }
    private val channelName by lazy { findViewById<EditText>(R.id.notification_channel_name_value) }
    private val showBadge by lazy { findViewById<CheckBox>(R.id.notification_box_badge) }
    private val showLight by lazy { findViewById<CheckBox>(R.id.notification_box_light) }
    private val notificationAlert by lazy { NotificationAlert.getInstance() }
    // private val notificationAlert by lazy { NotificationAlert.getInstanceV21() }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (manager.getNotificationChannel("test123") == null) {
                val channel = NotificationChannel("test123", "General", NotificationManager.IMPORTANCE_DEFAULT)
                channel.description = "TestNotification"
                channel.setShowBadge(false)
                channel.enableLights(false)
                manager.createNotificationChannel(channel)
            }

            val notification = Notification.Builder(this, "test123")
                    .setContentTitle("title")
                    .setContentText("text")
                    .setSmallIcon(R.drawable.notification_alert_ic)
                    .build()
            manager.notify(777, notification)
        }
    }

    fun onViewClick(view: View) {
        if (view.id == R.id.notification_btn_add) {
            notificationAlert.showNotification(this,
                    channelId.text.toString(),
                    channelName.text.toString(),
                    showBadge.isChecked,
                    showLight.isChecked)
        }
    }
}