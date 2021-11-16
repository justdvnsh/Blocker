package divyansh.tech.blocker.common

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.widget.Toast


class CallReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, intent: Intent?) {
        val state: String? = intent?.getStringExtra(TelephonyManager.EXTRA_STATE)

        state?.let {
            val number: String? = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
            if (number != null) Toast.makeText(p0, number, Toast.LENGTH_SHORT).show()
        }

        when(state) {
            null -> {
                Toast.makeText(p0, "Null state", Toast.LENGTH_SHORT).show()
            }
            TelephonyManager.EXTRA_STATE_RINGING -> {
                Toast.makeText(p0, "RINGING STATE", Toast.LENGTH_SHORT).show()
                val number: String? = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
                if (number != null) {
                    Toast.makeText(p0, number, Toast.LENGTH_SHORT).show()
                }
                else Toast.makeText(p0, "NUMBER IS NULL", Toast.LENGTH_SHORT).show()
            }
            TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                Toast.makeText(p0, "OFFHOOK STATE", Toast.LENGTH_SHORT).show()
            }
            TelephonyManager.EXTRA_STATE_IDLE -> {
                Toast.makeText(p0, "IDLE STATE", Toast.LENGTH_SHORT).show()
            }
        }

//        if (state == null) {
//
//            //Outgoing cal
//        } else if (state == TelephonyManager.EXTRA_STATE_OFFHOOK) {
//            Log.e("tag", "EXTRA_STATE_OFFHOOK")
//        } else if (state == TelephonyManager.EXTRA_STATE_IDLE) {
//            Log.e("tag", "EXTRA_STATE_IDLE")
//        } else if (state == TelephonyManager.EXTRA_STATE_RINGING) {
//
//            //Incoming call
//            val number: String = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
//            Log.e("tag", "Incoming number : $number")
//        } else Log.e("tag", "none")
    }
}