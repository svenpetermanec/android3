package hr.tvz.android.listapetermanec


import android.annotation.SuppressLint
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class Firebase : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val notification = message.notification

        NotificationCompat.Builder(this, "1")
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setContentTitle(notification?.title).setContentText(notification?.body).priority =
            NotificationCompat.PRIORITY_DEFAULT
    }
}