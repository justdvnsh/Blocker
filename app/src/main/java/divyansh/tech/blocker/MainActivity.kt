package divyansh.tech.blocker

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.telephony.TelephonyManager
import androidx.annotation.RequiresApi
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.blocker.common.CallReceiver
import divyansh.tech.blocker.common.IncomingCallReceiver

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val recv = IncomingCallReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        checkPermission()
    }



//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun checkPermission() {
//        Dexter.withContext(this).withPermission(Manifest.permission.ANSWER_PHONE_CALLS)
//            .withListener(object: PermissionListener {
//                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
//                    registerReceiver(recv, IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED))
//                }
//
//                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onPermissionRationaleShouldBeShown(
//                    p0: PermissionRequest?,
//                    p1: PermissionToken?
//                ) {
//                    p1?.continuePermissionRequest()
//                }
//
//            })
//    }

    override fun onStop() {
        super.onStop()
//        unregisterReceiver(recv)
    }
}