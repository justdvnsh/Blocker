package divyansh.tech.blocker.common

import android.Manifest
import android.R
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.telecom.TelecomManager
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.android.internal.telephony.ITelephony
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import divyansh.tech.blocker.MainActivity
import divyansh.tech.blocker.common.database.ContactDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.reflect.Method
import javax.inject.Inject


@AndroidEntryPoint
class IncomingCallReceiver: BroadcastReceiver() {

    @Inject
    lateinit var dao: ContactDao

    @Inject
    @ApplicationContext
    lateinit var context: Context

    private lateinit var blockedNumbers: List<ContactModel>

    @SuppressLint("SoonBlockedPrivateApi")
    override fun onReceive(context: Context, intent: Intent) {
        val telephonyService: ITelephony
        try {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            val number = intent.extras!!.getString(TelephonyManager.EXTRA_INCOMING_NUMBER)
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING, ignoreCase = true)) {
                val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                try {
                    val m: Method = tm.javaClass.getDeclaredMethod("getITelephony")
                    m.setAccessible(true)
                    telephonyService = m.invoke(tm) as ITelephony
                    if (number != null) {
                        Toast.makeText(context, "NUMBER IS NOT NULL", Toast.LENGTH_SHORT).show()
                        telephonyService.endCall()
                        Toast.makeText(context, "Ending the call from: $number", Toast.LENGTH_SHORT)
                            .show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                val telecomManager =
                    context.getSystemService(Context.TELECOM_SERVICE) as TelecomManager
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ANSWER_PHONE_CALLS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        Toast.makeText(context, "INSIDE TELECOM MANAGER", Toast.LENGTH_SHORT).show()
                        GlobalScope.launch(Dispatchers.IO) {
                            blockedNumbers = dao.getAllBlockedContacts()
                            blockedNumbers.forEach {
                                if (number.equals(it.phone)) {
                                    telecomManager.endCall()
                                    createNotificationChannel()
                                    addNotification(it.name, context)
                                }
                            }
                        }
                    }
                } else {
                    //Ask for permission here
                }
                Toast.makeText(context, "Ring $number", Toast.LENGTH_SHORT).show()
            }
            if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK, ignoreCase = true)) {
                Toast.makeText(context, "Answered $number", Toast.LENGTH_SHORT).show()
            }
            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE, ignoreCase = true)) {
                Toast.makeText(context, "Idle $number", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "blocker"
            val descriptionText = "Blocked contacts calling notification"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("blocker", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun addNotification(name: String, context: Context) {
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(context, "blocker")
            .setSmallIcon(R.drawable.gallery_thumb) //set icon for notification
            .setContentTitle("Blocker Blocked a contact") //set title of notification
            .setContentText("Blocker blocked a call from $name") //this is notification message
            .setAutoCancel(true) // makes auto cancel of notification
            .setPriority(NotificationCompat.PRIORITY_HIGH) //set priority of notification
        val notificationIntent = Intent(context, MainActivity::class.java)
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        //notification message will get at NotificationView
        notificationIntent.putExtra("message", "This is a notification message")
        val pendingIntent = PendingIntent.getActivity(
            context, 0, notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder.setContentIntent(pendingIntent)

        // Add as notification
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(0, builder.build())
    }
}