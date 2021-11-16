package divyansh.tech.blocker

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.telephony.TelephonyManager
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.blocker.common.CallReceiver

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val recv = CallReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerReceiver(recv, IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(recv)
    }
}