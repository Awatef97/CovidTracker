package iti.intake40.covidtracker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import iti.intake40.covidtracker.Work.WorkerNotification
import java.util.*

object Notification {
    fun sendNotification(applicationContext:Context) {
        var notificationId = Random().nextInt(10000)
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "Work_Manager"

        //If on Oreo then notification required a notification channel.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "WorkManager",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(WorkerNotification.countryTitle)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("New Cases " + WorkerNotification.countryNewCases + "\nNew Deaths " + WorkerNotification.countryNewDeaths + "\nTotal Cases " + WorkerNotification.countryTotalCases + "\nTotal Deaths " + WorkerNotification.countryTotalDeaths + "\nTotal Recovered " + WorkerNotification.countryTotalRecovered)
            )
            .setSmallIcon(R.drawable.ic_launcher_background)


        notificationManager.notify(notificationId, notification.build())
    }

}