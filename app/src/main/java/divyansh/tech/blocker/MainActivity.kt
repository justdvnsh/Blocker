package divyansh.tech.blocker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


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