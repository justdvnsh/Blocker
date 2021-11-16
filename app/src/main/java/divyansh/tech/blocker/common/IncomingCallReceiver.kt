package divyansh.tech.blocker.common

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.telecom.TelecomManager
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.lang.reflect.Method
import com.android.internal.telephony.ITelephony


class IncomingCallReceiver : BroadcastReceiver() {
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
                        if (number.equals("+916394015772")) telecomManager.endCall()
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
}