package com.example.purpleapp.api

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.purpleapp.MainActivity
import com.example.purpleapp.R


class MemoBroadcast():BroadcastReceiver() {
    override fun onReceive(context:Context,intent:Intent) {
        val repeating_Intent = Intent(context, MainActivity::class.java)
        repeating_Intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

         var pendingIntent:PendingIntent = PendingIntent.getActivity(context, 0, repeating_Intent, PendingIntent.FLAG_IMMUTABLE)
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, "Notification")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.affetta_logo_new)
                .setLargeIcon(
                    Bitmap.createScaledBitmap(
                        BitmapFactory.decodeResource(
                            context.getResources(),
                            R.drawable.affetta_logo_new
                        ), 128, 128, false
                    )
                )
                .setContentTitle("HOT DEALS")
                .setContentText("let's grab hot deals")
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(context)

        notificationManager.notify(200, builder.build())
        Log.i("@@@@","notified")

    }

}








